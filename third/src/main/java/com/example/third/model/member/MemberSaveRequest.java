package com.example.third.model.member;

import lombok.Data;

@Data
public class MemberSaveRequest {

    private String email;

    private Integer age;

    private String name;
}
