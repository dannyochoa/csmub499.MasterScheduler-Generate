package com.csumb.Generate;

import java.util.List;

import com.csumb.Generate.entities.Student;
import com.csumb.Generate.repositotries.IStudentRepository;
import com.csumb.Generate.repositotries.ITeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateController{
    @Autowired
    IStudentRepository student;
    ITeacherRepository teacher; 

    @GetMapping("/students")

    @ResponseBody
    List<Student> getStudents(){
        return null; 
    }
}
