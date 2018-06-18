package com.dmetal.ems.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dmetal.ems.dao.UserDAO;
import com.dmetal.ems.entity.User;

@Service("LoginService")
public class LoginServiceImpl implements LoginService{
	@Resource(name="userDAO")
	private UserDAO dao;
	public User checkLogin(String username, String pwd) {	
		User user=dao.findByUsername(username);
		if(user==null) {
			/*
			 * �û���������
			 * �����׳�һ��Ӧ���쳣
			 * ע����ʾ���ڲ���Ӧ���쳣֮����Ҫ��ȷ��ʾ�û���ȡ��ȷ�Ĳ���
			 */
			throw new ApplicationException("�û������������");	
		}
		if(!user.getPassword().equals(pwd)) {
			throw new ApplicationException("�û������������");
		}
		//��¼��֤ͨ��
		return user;
	}
	
}
