package com.dmetal.ems.dao;

import org.springframework.stereotype.Repository;

import com.dmetal.ems.annotations.MyBatisRepository;
import com.dmetal.ems.entity.User;


/**
 * ³Ö¾Ã²ã½Ó¿Ú
 * @author NiCo
 *
 */
@Repository("userDAO")
@MyBatisRepository
public interface UserDAO {
	public User findByUsername(String username);
}
