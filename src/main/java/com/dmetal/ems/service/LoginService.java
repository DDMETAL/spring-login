package com.dmetal.ems.service;

import com.dmetal.ems.entity.User;

/**
 * ҵ���ӿ�
 * @author NiCo
 *
 */
public interface LoginService {
	public User checkLogin(String username,String pwd);
}
