package vn.edu.usth.connect.Resource.Second_Third_Year.RecyclerView;

public class Sty_Item {

    String heading;
    String subhead;

    public Sty_Item(String heading,  String subhead) {
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
