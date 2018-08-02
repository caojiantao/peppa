package com.cjt.entity.model.system.security;

import com.cjt.entity.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息实体类
 *
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDO extends BaseDO {

    private String username;
    private String password;
    private String nickname;
}
