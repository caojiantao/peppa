package com.cjt.entity.dto;

import lombok.Data;

/**
 * 用户信息DTO
 *
 * @author caojiantao
 */
@Data
public class UserDTO extends BasePageDTO {

    private Long id;
    private String username;
    private String password;

    private UserDTO(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * 构造器模式，方便拓展参数
     */
    public static class Builder {

        private long id;

        private String username;

        private String password;

        public Builder id(long id) {
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
