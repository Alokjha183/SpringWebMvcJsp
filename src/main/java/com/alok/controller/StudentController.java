package com.alok.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.alok.Entity.StudentEntity;
import com.alok.Repository.StudentRepository;
import com.alok.bindings.Student;

@Controller
public class StudentController {
	@Autowired
	private StudentRepository repo;
	
	//method to load Student Form
	@GetMapping("/")
	public String loadForm(Model model) {
		
		loadFormData(model);
		
		
		
		return "index";
		
	}


	private void loadFormData(Model model) {
		List<String> coursesList=new ArrayList<>();
		coursesList.add("Java");
		coursesList.add("Devops");
		coursesList.add("AWS");
		coursesList.add("Python");
		
		List<String> timingsList=new ArrayList<>();
		timingsList.add("morning");
		timingsList.add("Afternoon");
		timingsList.add("evening");
		
		Student student=new Student();
		model.addAttribute("courses",coursesList);
		model.addAttribute("timings", timingsList);
		model.addAttribute("student", student);
	}
	
	
	//method to save student form data
	@PostMapping("/save")
	public String handleSubmit(Student s,Model model) {
		System.out.println(s);
		//logic to save
		StudentEntity entity=new StudentEntity();
		//copy the data from binding to Entity
		BeanUtils.copyProperties(s, entity);
		
		entity.setTimings(Arrays.toString(s.getTimings()));
		
		repo.save(entity);
		
		 model.addAttribute("msg","Student saved");
		 
		 loadFormData(model);
		 
		 return "index";
		 
		
		
	}
	
	
	
	//method to display saved Students Data
	@GetMapping("/viewStudents")
	public String getStudentsData(Model model) {
		//logic to fectch and send the data to UI
		List<StudentEntity> studentList = repo.findAll();
		model.addAttribute("students", studentList);
		return "data";
	}

}
