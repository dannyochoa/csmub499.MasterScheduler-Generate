package com.csumb.Generate.seeders;

import com.csumb.Administrative.entities.Student;
import com.csumb.Administrative.repositotries.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StudentSeeder implements CommandLineRunner {
    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public void run(String... args)throws  Exception{
        Student s1 = new Student("123", "Edith",9);
        Student s2 = new Student("345", "Daniel", 10);
        Student s3 = new Student("567", "Judith", 11);
        Student s4 = new Student("789", "Manjit", 12);

        studentRepository.deleteAll();
        List<Student> students = Arrays.asList(s1,s2,s3,s4);
        studentRepository.saveAll(students);
    }
}
