package com.studentmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentmanagement.dto.StudentRequestDto;
import com.studentmanagement.dto.StudentResponseDto;

@Repository
public class StudentDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(StudentRequestDto dto) {
		int result = 0;
		String sql = "insert into student (student_id,student_name,class_name,register_date,status) values(?,?,?,?,?)";

		result = jdbcTemplate.update(sql, dto.getStudentId(), dto.getStudentName(), dto.getClassName(),
				dto.getRegisterDate(), dto.getStatus());

		return result;
	}

	public int update(StudentRequestDto dto) {
		int result = 0;

		String sql = "update student set student_name=?,class_name=?,register_date=?,status=? where student_id=?";

		result = jdbcTemplate.update(sql, dto.getStudentName(), dto.getClassName(), dto.getRegisterDate(),
				dto.getStatus(), dto.getStudentId());

		return result;
	}

	public int delete(StudentRequestDto dto) {
		int result = 0;

		String sql = "delete from student where student_id=?";

		result = jdbcTemplate.update(sql, dto.getStudentId());

		return result;
	}

	public List<StudentResponseDto> select(StudentRequestDto dto) {
		List<StudentResponseDto> studentList = new ArrayList<StudentResponseDto>();

		String searchAll = "select * from student";
		String searchFilter = "select * from student where student_id=? or student_name=? or class_name=?";

		if (!dto.getStudentId().equals("") || !dto.getStudentName().equals("") || !dto.getClassName().equals("")) {
			studentList = jdbcTemplate.query(searchFilter,
					(rs, rowNum) -> new StudentResponseDto(rs.getString("student_id"), rs.getString("student_name"),
							rs.getString("class_name"), rs.getDate("register_date"), rs.getString("status")),
					dto.getStudentId(), dto.getStudentName(), dto.getClassName());
		} else{
			studentList = jdbcTemplate.query(searchAll,
					(rs, rowNum) -> new StudentResponseDto(rs.getString("student_id"), rs.getString("student_name"),
							rs.getString("class_name"), rs.getDate("register_date"), rs.getString("status")));
		}
		return studentList;
	}

}
