//This class is not used anymore. MyReviews class can be used instead.
package ir.rayapars.honarfakher.classes;

public class Comment {
    public String userName;
    public String userFullName;
    public String title;
    public String comment;
    public String date;


    public Comment() {
    }

    public Comment(String userName, String userFullName, String title, String comment, String date) {
        this.userName = userName;
        this.userFullName = userFullName;
        this.title = title;
        this.comment = comment;
        this.date = date;
    }
}
