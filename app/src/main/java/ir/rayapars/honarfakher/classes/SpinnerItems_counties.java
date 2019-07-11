package ir.rayapars.honarfakher.classes;

public class SpinnerItems_counties {

    public String title;
    public String id;


    public SpinnerItems_counties(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
