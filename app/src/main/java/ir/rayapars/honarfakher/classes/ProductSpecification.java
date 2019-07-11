package ir.rayapars.honarfakher.classes;

public class ProductSpecification {

    private String id;
    private String title;
    private String titleContent;
    public String categoryID;

    public ProductSpecification(String id, String title, String titleContent) {
        this.id = id;
        this.title = title;
        this.titleContent = titleContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public ProductSpecification(String id, String title, String titleContent, String categoryID) {
        this.id = id;
        this.title = title;
        this.titleContent = titleContent;
        this.categoryID = categoryID;
    }
}




