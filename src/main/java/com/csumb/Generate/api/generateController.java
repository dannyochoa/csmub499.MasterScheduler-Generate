package com.csumb.Generate.api;

import java.util.List;

import com.csumb.Generate.api.entities.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateController{
    @Autowired
    Student student;

    @GetMapping("/students")

    @ResponseBody
    List<Student> getStudents(){
        return null; 
    }
}
