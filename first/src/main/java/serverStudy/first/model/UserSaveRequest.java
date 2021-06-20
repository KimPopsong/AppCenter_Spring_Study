package serverStudy.first.model;

import lombok.Data;

@Data
public class UserSaveRequest {

    private String name;

    private String email;

    private int age;
}
