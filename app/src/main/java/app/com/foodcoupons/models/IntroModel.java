package app.com.foodcoupons.models;

/**
 * Created by dev on 26/9/18.
 */

public class IntroModel {
    String title;
    String description;
    Integer Image;

    public IntroModel(String title, String description, Integer image) {
        this.title = title;
        this.description = description;
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }
}
