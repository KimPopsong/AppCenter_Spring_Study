package inu.appcenter.seventh.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA에서는 기본 생성자가 필요하기 때문에
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded  // 값 객체
    private Image image;

    public static Member createMember(String name) {
        Member member = new Member();

        member.name = name;

        return member;
    }

    public void changeImage(Image imageObject) {

        this.image = imageObject;
    }
}
