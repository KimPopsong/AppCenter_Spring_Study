package com.example.fifth.model.member;

import lombok.Data;

@Data
public class MemberSaveRequest {

    private String email;

    private String password;

    private String name;
}
