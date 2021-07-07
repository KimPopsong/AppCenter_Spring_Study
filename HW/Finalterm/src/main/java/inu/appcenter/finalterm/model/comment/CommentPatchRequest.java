package inu.appcenter.finalterm.model.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentPatchRequest {

    @NotBlank
    private String content;
}
