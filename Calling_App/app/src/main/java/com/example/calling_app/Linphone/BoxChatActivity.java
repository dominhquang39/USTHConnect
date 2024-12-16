package com.example.calling_app.Linphone;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calling_app.IncomingCallActivity;
import com.example.calling_app.MyApplication;
import com.example.calling_app.R;

import org.linphone.core.Core;
import org.linphone.core.*;


public class BoxChatActivity extends AppCompatActivity {

    private Core outgoing_core;

    private String username;
    private String password;
    private String box_chat;
    public String domain = "sip.linphone.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_box_chat);

        // Incoming Call
        Intent intent = getIntent();
        username = intent.getStringExtra("sip_username");
        password = intent.getStringExtra("sip_password");
        box_chat = intent.getStringExtra("BoxChat_Name");

        TextView textView = findViewById(R.id.box_chat_username);
        textView.setText(box_chat);


        // Outgoing call
        Factory factory2 = Factory.instance();
        factory2.setDebugMode(true, "Hello Linphone Outcoming");
        outgoing_core = factory2.createCore(null, null, this);

        outgoing_login(username, password);

        ImageButton call_button = findViewById(R.id.calling_button);
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.header_layout).setVisibility(View.GONE);
                findViewById(R.id.outgoing_call_layout).setVisibility(View.VISIBLE);

                outgoing_core.setNativeVideoWindowId(findViewById(R.id.remote_video_surface));
                outgoing_core.setNativePreviewWindowId(findViewById(R.id.local_preview_video_surface));
                outgoing_core.enableVideoCapture(true);
                outgoing_core.enableVideoDisplay(true);

                outgoing_core.getVideoActivationPolicy().setAutomaticallyAccept(true);

                outgoingCall();

                ((TextView) findViewById(R.id.outgoing_remote_address)).setText(box_chat);

                findViewById(R.id.outgoing_hang_up).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_hang_up).setEnabled(true);

                findViewById(R.id.outgoing_hang_up).setOnClickListener(v -> hangUp());
                findViewById(R.id.outgoing_pause).setOnClickListener(v -> pauseOrResume());
                findViewById(R.id.outgoing_toggle_video).setOnClickListener(v -> toggleVideo());
                findViewById(R.id.outgoing_toggle_camera).setOnClickListener(v -> toggleCamera());

                findViewById(R.id.outgoing_pause).setEnabled(false);
                findViewById(R.id.outgoing_toggle_video).setEnabled(false);
                findViewById(R.id.outgoing_toggle_camera).setEnabled(false);


            }
        });
    }

    // Incoming CoreListenerStub
    private final CoreListenerStub incomingCallCoreListener = new CoreListenerStub() {
        @Override
        public void onAccountRegistrationStateChanged(Core core, Account account, RegistrationState state, String message) {
            ((TextView) findViewById(R.id.registration_status)).setText(message);
        }

        @Override
        public void onAudioDeviceChanged(Core core, AudioDevice audioDevice) {
            // This callback will be triggered when a successful audio device has been changed
        }

        @Override
        public void onAudioDevicesListUpdated(Core core) {
            // This callback will be triggered when the available devices list has changed,
            // for example after a bluetooth headset has been connected/disconnected.
        }

        @Override
        public void onCallStateChanged(Core core, Call call, Call.State state, String message) {
            findViewById(R.id.header_layout).setVisibility(View.GONE);

            // When a call is received
            if (state == Call.State.IncomingReceived) {
                boolean isScreenLocked = isScreenLocked();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String channelId = MyApplication.CHANNEL_ID;
                String channelName = "Incoming Call Notifications";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent fullScreenIntent = new Intent(BoxChatActivity.this, IncomingCallActivity.class);
                fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                fullScreenIntent.putExtra("CALL_ID", call.getCallLog() != null ? call.getCallLog().getCallId() : null);
                fullScreenIntent.putExtra("username", username);
                fullScreenIntent.putExtra("password", password);
                fullScreenIntent.putExtra("domain", domain);
                fullScreenIntent.putExtra("transport_type", TransportType.Tls);

                PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(
                        BoxChatActivity.this,
                        0,
                        fullScreenIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                NotificationCompat.Builder notificationBuilder;

                if (isScreenLocked) {
                    // Full-screen notification for locked screen
                    notificationBuilder = new NotificationCompat.Builder(BoxChatActivity.this, channelId)
                            .setContentTitle(call.getRemoteAddress().getUsername())
                            .setContentText("You have an incoming call")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setFullScreenIntent(fullScreenPendingIntent, true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setCategory(NotificationCompat.CATEGORY_CALL);
                } else {
                    // Heads-up notification for unlocked screen
                    notificationBuilder = new NotificationCompat.Builder(BoxChatActivity.this, channelId)
                            .setContentTitle(call.getRemoteAddress().getUsername())
                            .setContentText("You have an incoming call")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(fullScreenPendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_CALL);
                }

                notificationManager.notify(1, notificationBuilder.build());

                ((TextView) findViewById(R.id.incoming_remote_address)).setText(call.getRemoteAddress().getUsername());
                String remoteAdress = call.getRemoteAddress().asStringUriOnly();
            } else if (state == Call.State.Connected) {
                findViewById(R.id.incoming_mute_mic).setEnabled(true);
                findViewById(R.id.incoming_toggle_speaker).setEnabled(true);

                findViewById(R.id.incoming_toggle_speaker).setVisibility(View.VISIBLE);
                findViewById(R.id.incoming_mute_mic).setVisibility(View.VISIBLE);

            } else if (state == Call.State.Released) {

                findViewById(R.id.incoming_hang_up).setEnabled(false);
                findViewById(R.id.incoming_answer).setEnabled(false);
                findViewById(R.id.incoming_mute_mic).setEnabled(false);
                findViewById(R.id.incoming_toggle_speaker).setEnabled(false);

                ((TextView) findViewById(R.id.incoming_remote_address)).setText("");

                findViewById(R.id.incoming_call_layout).setVisibility(View.GONE);
                findViewById(R.id.header_layout).setVisibility(View.VISIBLE);
            }
        }

    };

    // Outgoing CoreListener
    private final CoreListenerStub outgoingCallCoreListener = new CoreListenerStub() {
        @Override
        public void onAccountRegistrationStateChanged(Core core, Account account, RegistrationState state, String message) {
            ((TextView) findViewById(R.id.outgoing_registration_status)).setText(message);

        }

        @Override
        public void onCallStateChanged(Core core, Call call, Call.State state, String message) {

            if (state == Call.State.OutgoingInit) {
            } else if (state == Call.State.IncomingReceived) {
                boolean isScreenLocked = isScreenLocked();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String channelId = "incoming_call_channel";
                String channelName = "Incoming Call Notifications";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent fullScreenIntent = new Intent(BoxChatActivity.this, IncomingCallActivity.class);
                fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                fullScreenIntent.putExtra("CALL_ID", call.getCallLog() != null ? call.getCallLog().getCallId() : null);
                fullScreenIntent.putExtra("username", username);
                fullScreenIntent.putExtra("password", password);
                fullScreenIntent.putExtra("domain", domain);
                fullScreenIntent.putExtra("transport_type", TransportType.Tls);

                PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(
                        BoxChatActivity.this,
                        0,
                        fullScreenIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                NotificationCompat.Builder notificationBuilder;

                if (isScreenLocked) {
                    // Full-screen notification for locked screen
                    notificationBuilder = new NotificationCompat.Builder(BoxChatActivity.this, channelId)
                            .setContentTitle(call.getRemoteAddress().getUsername())
                            .setContentText("You have an incoming call")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setFullScreenIntent(fullScreenPendingIntent, true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setCategory(NotificationCompat.CATEGORY_CALL);
                } else {
                    // Heads-up notification for unlocked screen
                    notificationBuilder = new NotificationCompat.Builder(BoxChatActivity.this, channelId)
                            .setContentTitle(call.getRemoteAddress().getUsername())
                            .setContentText("You have an incoming call")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(fullScreenPendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_CALL);
                }

                notificationManager.notify(1, notificationBuilder.build());

                ((TextView) findViewById(R.id.incoming_remote_address)).setText(call.getRemoteAddress().getUsername());
            }
            else if (state == Call.State.OutgoingProgress) {
            } else if (state == Call.State.OutgoingRinging) {
            } else if (state == Call.State.Connected) {
            } else if (state == Call.State.StreamsRunning) {
                findViewById(R.id.outgoing_hang_up).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_hang_up).setEnabled(true);

                findViewById(R.id.outgoing_pause).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_pause).setEnabled(true);
                ((Button) findViewById(R.id.outgoing_pause)).setText("Pause");

                findViewById(R.id.outgoing_toggle_video).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_toggle_video).setEnabled(true);

                findViewById(R.id.outgoing_toggle_camera).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_toggle_camera).setEnabled(
                        core.getVideoDevicesList().length > 2 && call.getCurrentParams().videoEnabled()
                );
            } else if (state == Call.State.Paused) {
                ((Button) findViewById(R.id.outgoing_pause)).setText("Resume");
                findViewById(R.id.outgoing_toggle_video).setEnabled(false);
            } else if (state == Call.State.PausedByRemote) {
            } else if (state == Call.State.Updating) {
            } else if (state == Call.State.UpdatedByRemote) {
            } else if (state == Call.State.Released) {
                ((TextView) findViewById(R.id.outgoing_remote_address)).setText("");

                findViewById(R.id.outgoing_pause).setEnabled(false);
                ((Button) findViewById(R.id.outgoing_pause)).setText("Pause");

                findViewById(R.id.outgoing_toggle_video).setEnabled(false);

                findViewById(R.id.outgoing_hang_up).setEnabled(false);

                findViewById(R.id.outgoing_toggle_camera).setEnabled(false);

                findViewById(R.id.header_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_call_layout).setVisibility(View.GONE);

            } else if (state == Call.State.Error) {
            }
        }
    };

    private boolean isScreenLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardLocked() || keyguardManager.isDeviceLocked();
    }

    // OutgoingCall
    private void outgoingCall() {
        String remoteSipUri = String.format("sip:%s@sip.linphone.org", box_chat);
        Address remoteAddress = Factory.instance().createAddress(remoteSipUri);
        if (remoteAddress == null) return;

        CallParams params = outgoing_core.createCallParams(null);
        if (params == null) return;

        params.setMediaEncryption(MediaEncryption.None);
        outgoing_core.inviteAddressWithParams(remoteAddress, params);
    }

    // Outgoing Hangup
    private void hangUp() {
        if (outgoing_core.getCallsNb() == 0) return;

        // Get the current call or fallback to the first call in the list
        Call call = outgoing_core.getCurrentCall() != null ? outgoing_core.getCurrentCall() : outgoing_core.getCalls()[0];
        if (call == null) return;

        // Terminate the call
        call.terminate();
    }

    // Outgoing ToggleVideo
    private void toggleVideo() {
        if (outgoing_core.getCallsNb() == 0) return;

        Call call = outgoing_core.getCurrentCall() != null ? outgoing_core.getCurrentCall() : outgoing_core.getCalls()[0];
        if (call == null) return;

        // Check for CAMERA permission
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            return;
        }

        // Create new call parameters from the call object
        CallParams params = outgoing_core.createCallParams(call);
        if (params != null) {
            // Toggle video state
            params.enableVideo(!call.getCurrentParams().videoEnabled());
            // Request the call update
            call.update(params);
        }
    }

    // Outgoing toggleCamera
    private void toggleCamera() {
        String currentDevice = outgoing_core.getVideoDevice();

        for (String camera : outgoing_core.getVideoDevicesList()) {
            if (!camera.equals(currentDevice) && !camera.equals("StaticImage: Static picture")) {
                outgoing_core.setVideoDevice(camera);
                break;
            }
        }
    }

    // Outgoing Pause/Resume => Wait
    private void pauseOrResume() {
        if (outgoing_core.getCallsNb() == 0) return;

        Call call = outgoing_core.getCurrentCall() != null ? outgoing_core.getCurrentCall() : outgoing_core.getCalls()[0];
        if (call == null) return;

        // Pause or resume the call based on its state
        if (call.getState() != Call.State.Paused && call.getState() != Call.State.Pausing) {
            call.pause();
        } else if (call.getState() != Call.State.Resuming) {
            call.resume();
        }
    }


    // Outgoing Login
    private void outgoing_login(String username, String password) {
        domain = "sip.linphone.org";

        TransportType transportType = TransportType.Tls;

        AuthInfo authInfo = Factory.instance().createAuthInfo(username, null, password, null, null, domain, null);

        AccountParams params = outgoing_core.createAccountParams();
        Address identity = Factory.instance().createAddress("sip:" + username + "@" + domain);
        params.setIdentityAddress(identity);

        Address address = Factory.instance().createAddress("sip:" + domain);
        if (address != null) {
            address.setTransport(transportType);
        }

        params.setServerAddress(address);
        params.setRegisterEnabled(true);

        Account account = outgoing_core.createAccount(params);

        outgoing_core.addAuthInfo(authInfo);
        outgoing_core.addAccount(account);

        // Outgoing Video
        outgoing_core.getConfig().setBool("video", "auto_resize_preview_to_keep_ratio", true);

        outgoing_core.setDefaultAccount(account);
        outgoing_core.addListener(outgoingCallCoreListener);
        outgoing_core.start();

        // We will need the RECORD_AUDIO permission for video call
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }
    }
}