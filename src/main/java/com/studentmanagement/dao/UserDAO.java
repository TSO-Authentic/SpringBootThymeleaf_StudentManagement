package com.studentmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentmanagement.dto.UserRequestDto;
import com.studentmanagement.dto.UserResponseDto;

@Repository
public class UserDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insertUser(UserRequestDto dto) {
		int result = 0;
		String sql = "insert into user (id,name,password) values (?,?,?)";

		result = jdbcTemplate.update(sql, dto.getId(), dto.getName(), dto.getPassword());

		return result;
	}

	public int update(UserRequestDto dto) {
		int result = 0;
		String sql = "update user set name=?,password=? where id=?";
		result = jdbcTemplate.update(sql, dto.getName(), dto.getPassword(), dto.getId());
		return result;
	}

	public int delete(UserRequestDto dto) {
		int result = 0;
		String sql = "delete from user where id=?";
		result = jdbcTemplate.update(sql, dto.getId());
		return result;
	}

	public List<UserResponseDto> selectUser(UserRequestDto dto) {

		List<UserResponseDto> list = new ArrayList<UserResponseDto>();

		String searchAll = "select * from user";
		String searchFilter = "select * from user where id=? or name=?";

		if (!dto.getId().equals("") || !dto.getName().equals("")) {
			list = jdbcTemplate.query(searchFilter, (rs, rowNum) -> new UserResponseDto(rs.getString("id"),
					rs.getString("name"), rs.getString("password")), dto.getId(), dto.getName());
		} else {
			list = jdbcTemplate.query(searchAll, (rs, rowNum) -> new UserResponseDto(rs.getString("id"),
					rs.getString("name"), rs.getString("password")));
		}
		return list;
	}

}