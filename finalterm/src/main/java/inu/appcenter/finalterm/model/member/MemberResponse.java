package inu.appcenter.finalterm.model.member;

import inu.appcenter.finalterm.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponse {

    private String email;
    private String nickName;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public MemberResponse(Member member) {

        this.email = member.getEmail();
        this.nickName = member.getNickName();
        this.createdAt = member.getCreatedAt();
        this.updateAt = member.getUpdateAt();
    }
}
