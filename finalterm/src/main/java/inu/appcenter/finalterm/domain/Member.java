package inu.appcenter.finalterm.domain;

import inu.appcenter.finalterm.domain.status.MemberStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    public static Member createMember(String email, String password, String nickName) {

        Member member = new Member();

        member.email = email;
        member.password = password;
        member.nickName = nickName;
        member.status = MemberStatus.ACTIVATED;
        member.roles.add(Role.createRole(member));

        return member;
    }

    public static Member createADMIN(String password) {

        Member member = new Member();

        member.email = "ADMIN";
        member.password = password;
        member.nickName = "ADMIN";
        member.status = MemberStatus.ACTIVATED;
        member.roles.add(Role.createADMIN(member));

        return member;
    }

    public void patch(String password, String nickName) {

        this.password = password;
        this.nickName = nickName;
    }

    public void delete() {

        this.status = MemberStatus.DELETED;
    }
}
