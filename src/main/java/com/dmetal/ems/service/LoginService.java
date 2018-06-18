package com.dmetal.ems.service;

import com.dmetal.ems.entity.User;

/**
 * 业务层接口
 * @author NiCo
 *
 */
public interface LoginService {
	public User checkLogin(String username,String pwd);
}
