package inu.appcenter.finalterm.model.image;

import inu.appcenter.finalterm.domain.Image;
import lombok.Data;

@Data
public class ImageDTO {

    private String imageUrl;

    public ImageDTO(Image image) {

        this.imageUrl = image.getImageUrl();
    }
}
