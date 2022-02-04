package com.studentmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentmanagement.dto.ClassRequestDto;
import com.studentmanagement.dto.ClassResponseDto;

@Repository
public class ClassDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insertClass(ClassRequestDto dto) {
		int result = 0;

		String sql = "insert into class (id, name) values (?, ?)";

		result = jdbcTemplate.update(sql, dto.getId(), dto.getName());

		return result;
	}

	public List<ClassResponseDto> select(ClassRequestDto dto) {
		List<ClassResponseDto> list = new ArrayList<ClassResponseDto>();
		
		String sql = "SELECT * FROM class WHERE id=? OR name=?";
		
			if(!dto.getId().equals("") || !dto.getName().equals("")) {
				list= jdbcTemplate.query(sql,
					(rs, rowNum) -> new ClassResponseDto(rs.getString("id"), rs.getString("name")), dto.getId(),
					dto.getName());
			}else if(dto.getId().equals("") || dto.getName().equals("")) {
				sql = "SELECT * FROM class";
				list = jdbcTemplate.query(sql,
	                    (rs, rowNum) -> new ClassResponseDto(rs.getString("id"), rs.getString("name")));

			}
			return list;
	}

//	public List<ClassResponseDto> selectAll() {
//		List<ClassResponseDto> list = new ArrayList<ClassResponseDto>();	
//		String sql = "SELECT * FROM class";
//		
//		list = jdbcTemplate.query(sql,
//                (rs, rowNum) -> new ClassResponseDto(rs.getString("id"), rs.getString("name")));
//		System.out.println(list.toString());
//		return list;
//	}
}