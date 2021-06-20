package appcenter.sixth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member createMember(String name, int age) {
        Member member = new Member();

        member.name = name;
        member.age = age;
        member.status = MemberStatus.ACTIVATED;

        return member;
    }

    public void update(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void delete() {
        this.status = MemberStatus.DELETED;
    }

    public static Member createMember(Long id, String name, int age) {
        Member member = new Member();

        member.id = id;
        member.name = name;
        member.age = age;
        member.status = MemberStatus.ACTIVATED;

        return member;
    }
}
