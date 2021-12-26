package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Student;
import com.example.demo.service.Studentservice;

//creating RestController  
@RestController
@RequestMapping("/student")
public class StudentController {
	// autowired the StudentService class
	private static final Logger logger = LogManager.getLogger(StudentController.class);

	@Autowired
	Studentservice studentService;

	// creating a get mapping that retrieves all the students detail from the
	// database
	@GetMapping("/all")
	private List<Student> getAllStudent() {
		return studentService.getAllStudent();
	}

	// creating a get mapping that retrieves the detail of a specific student
	@GetMapping("/getstudent/{id}")
	private Student getStudent(@PathVariable("id") int id) {
		Student s;
		try {
			logger.info("fetching student info:"+id);
			s = studentService.getStudentById(id);
		} catch (java.util.NoSuchElementException e) {
			logger.error("error while fetching student info+");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Data not found for Id:" + id);

		}
		return s;
	}

	// creating a delete mapping that deletes a specific student
	@DeleteMapping("/deletestudent/{id}")
	private void deleteStudent(@PathVariable("id") int id) {
		logger.info("deleting a student:" + id);
		studentService.delete(id);
	}

	// creating post mapping that post the student detail in the database
	@PostMapping("/savestudent")
	@ResponseBody
	private int saveStudent(@RequestBody Student student) {
		studentService.saveOrUpdate(student);
		return student.getId();
	}
}
