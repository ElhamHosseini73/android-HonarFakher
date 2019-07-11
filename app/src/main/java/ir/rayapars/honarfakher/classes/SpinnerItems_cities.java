package ir.rayapars.honarfakher.classes;

public class SpinnerItems_cities {

    public String title;
    public String id;

    public SpinnerItems_cities(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
