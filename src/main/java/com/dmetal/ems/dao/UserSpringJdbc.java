package com.dmetal.ems.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dmetal.ems.entity.User;

//@Repository("userDAO")
public class UserSpringJdbc implements UserDAO{
	@Autowired
	@Qualifier("jt")
	private JdbcTemplate jt;
	public User findByUsername(String username) {
		String sql="SELECT * "+
				   "FROM t_user "+
				   "WHERE username=? ";
		Object[] arg= {username};
		User user=null;
		try {
			user=jt.queryForObject(sql, arg,new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return user;
	}
	class UserRowMapper implements RowMapper<User>{

		public User mapRow(ResultSet rs, int index) throws SQLException {
			User user=new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setGendar(rs.getString("gendar"));
			return user;
		}
		
	}
}
