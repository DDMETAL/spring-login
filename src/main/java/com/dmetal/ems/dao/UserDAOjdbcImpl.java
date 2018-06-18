package com.dmetal.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.dmetal.ems.entity.User;

/**
 * 持久层实现类
 * @author NiCo
 *
 */
//@Repository("userDAO")
public class UserDAOjdbcImpl implements UserDAO{
	@Resource(name="ds")
	private DataSource ds;
	public User findByUsername(String username) {
		User user=null;
		Connection conn=null;
		try {
			conn=ds.getConnection();
			String sql="SELECT * "+
					   "FROM t_user "+
					   "WHERE username=? ";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setGendar(rs.getString("gendar"));
			}
		} catch (SQLException e) {
			//记日志
			e.printStackTrace();
			/*
			 * 如果是系统异常，应该提示用户稍后重试
			 * 此处直接将系统异常抛出即可
			 * 注：表示层可以统一处理系统异常
			 * 		给用户相应提示
			 */
			throw new RuntimeException(e);
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		
		return user;
	}

}
