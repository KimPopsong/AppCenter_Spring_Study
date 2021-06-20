package com.example.fifth.model.member;

import lombok.Data;

@Data
public class MemberLoginRequest {

    private String email;
    private String password;
}
