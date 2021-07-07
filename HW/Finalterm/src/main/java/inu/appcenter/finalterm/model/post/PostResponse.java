package inu.appcenter.finalterm.model.post;

import inu.appcenter.finalterm.domain.Post;
import inu.appcenter.finalterm.model.comment.CommentDTO;
import inu.appcenter.finalterm.model.image.ImageDTO;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostResponse {

    private String title;
    private String content;
    private String memberName;
    private List<CommentDTO> commentList;
    private List<ImageDTO> imageList;

    public PostResponse(Post post) {

        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberName = post.getMember().getNickName();
        this.commentList = post.getCommentList().stream().map(CommentDTO::new).collect(Collectors.toList());
        this.imageList = post.getImageList().stream().map(ImageDTO::new).collect(Collectors.toList());
    }
}
