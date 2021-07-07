package inu.appcenter.finalterm.model.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberSaveRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;
}