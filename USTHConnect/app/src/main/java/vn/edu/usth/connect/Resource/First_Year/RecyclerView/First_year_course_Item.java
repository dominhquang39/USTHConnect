package vn.edu.usth.connect.Resource.First_Year.RecyclerView;

public class First_year_course_Item {

    String heading;
    String subhead;

    public First_year_course_Item(String heading,  String subhead) {
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
