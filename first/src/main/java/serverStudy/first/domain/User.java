package serverStudy.first.domain;

import lombok.Getter;  // Getter와 Setter 코드 길이 줄여줌
import lombok.Setter;

/**
 * 회원 관리
 */

@Getter  // Getter 자동 생성
@Setter  // Setter 자동 생성
public class User {

    private Long id;  // Primary Key

    private String name;

    private String email;

    private int age;

    public static User createUser(String name, String email, int age) {
        User user = new User();
        user.age = age;
        user.name = name;
        user.email = email;

        return user;
    }
}
