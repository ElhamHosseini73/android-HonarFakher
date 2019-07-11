package ir.rayapars.honarfakher.classes;

public class SpinnerItems_years {
    public String title;
    public String id;

    public SpinnerItems_years(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
