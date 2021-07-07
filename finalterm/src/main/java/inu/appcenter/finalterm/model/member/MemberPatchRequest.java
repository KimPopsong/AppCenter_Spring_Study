package inu.appcenter.finalterm.model.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberPatchRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;
}
