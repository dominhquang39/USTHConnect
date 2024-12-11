package vn.edu.usth.connect.Schedule.Course.RecyclerView;

public class CourseItem {

    String heading;
    String subhead;

    public CourseItem(String heading, String subhead){
        this.heading = heading;
        this.subhead = subhead;
    }

    public String getHeading() {
        return heading;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }
}
