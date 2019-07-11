package ir.rayapars.honarfakher.classes;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class Category extends BaseObservable implements Parcelable {

    private String id;
    private String title;
    private String icon;

    public Category(String id, String title, String icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    protected Category(Parcel in) {
        id = in.readString();
        title = in.readString();
        icon = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(icon);
    }
}
