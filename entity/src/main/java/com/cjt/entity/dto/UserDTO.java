package com.cjt.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息DTO
 *
 * @author caojiantao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BasePageDTO {

    private Integer id;
    private String username;
    private String password;

    /**
     * 构造器模式，方便拓展参数
     */
    public static class Builder {

        private int id;
        private String username;
        private String password;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(id, username, password);
        }
    }
}
