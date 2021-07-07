package inu.appcenter.finalterm.model.post;

import inu.appcenter.finalterm.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {

    private Long categoryId;
    private String title;
    private String content;
    //private List<Image> imageList;
    private LocalDateTime updatedAt;

    public PostDTO(Post post) {

        this.categoryId = post.getCategory().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        //this.imageList = post.getImageList();
        this.updatedAt = post.getUpdateAt();
    }
}
