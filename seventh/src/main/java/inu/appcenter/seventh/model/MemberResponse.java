package inu.appcenter.seventh.model;

import inu.appcenter.seventh.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String name;
    private String profileImageUrl;
    private String thumbnailImageUrl;
    private String imageStoreName;

    public static MemberResponse from(Member member) {
        MemberResponse memberResponse = new MemberResponse();

        memberResponse.id = member.getId();
        memberResponse.name = member.getName();
        memberResponse.profileImageUrl = member.getImage().getProfileImageUrl();
        memberResponse.thumbnailImageUrl = member.getImage().getThumbnailImageUrl();

        return memberResponse;
    }
}
