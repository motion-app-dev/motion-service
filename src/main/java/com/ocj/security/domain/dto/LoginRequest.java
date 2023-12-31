package com.ocj.security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * 邮箱
     */

    private String email;

    /**
     * 密码
     */
    private String password;
}
