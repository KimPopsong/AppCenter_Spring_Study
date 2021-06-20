package appcenter.sixth.model;

import appcenter.sixth.domain.Member;
import appcenter.sixth.domain.MemberStatus;
import lombok.Data;

@Data
public class MemberResponse {

    private Long id;

    private String name;

    private Integer age;

    private MemberStatus status;

    public static MemberResponse from(Member member) {
        MemberResponse memberResponse = new MemberResponse();

        memberResponse.id = member.getId();
        memberResponse.age = member.getAge();
        memberResponse.name = member.getName();
        memberResponse.status = member.getStatus();

        return memberResponse;
    }
}
