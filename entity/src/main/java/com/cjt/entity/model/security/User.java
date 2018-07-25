package com.cjt.entity.model.security;

import lombok.Data;

/**
 * 用户信息实体类
 *
 * @author caojiantao
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
}
