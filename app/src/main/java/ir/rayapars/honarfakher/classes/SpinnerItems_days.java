package ir.rayapars.honarfakher.classes;

public class SpinnerItems_days {

    public String id;
    public String title;

    public SpinnerItems_days(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
