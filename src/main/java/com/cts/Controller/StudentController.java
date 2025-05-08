package com.cts.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.Module.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	
	private List<Student>student=new ArrayList<>(List.of(
			new Student(1,"Divith",200),
			new Student(2,"Nishanth",300)));
	@GetMapping("/Student")
	public List<Student>getStudents(){
		return student;
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	@PostMapping("/Student")
	public Student postStudents(@RequestBody Student stud){
		student.add(stud);
		return stud;
	}
	
}
