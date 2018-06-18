package com.dmetal.ems.dao;

import org.springframework.stereotype.Repository;

import com.dmetal.ems.annotations.MyBatisRepository;
import com.dmetal.ems.entity.User;


/**
 * �־ò�ӿ�
 * @author NiCo
 *
 */
@Repository("userDAO")
@MyBatisRepository
public interface UserDAO {
	public User findByUsername(String username);
}
