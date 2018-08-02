package com.cjt.dao.system.security;

import com.cjt.dao.IBaseDAO;
import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.system.security.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户实体数据持久层
 *
 * @author caojiantao
 */
public interface IUserDAO extends IBaseDAO<UserDO, UserDTO>{

    UserDO login(@Param("username") String username, @Param("password") String password);
}
