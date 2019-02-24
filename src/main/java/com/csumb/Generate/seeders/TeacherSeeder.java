package com.csumb.Generate.seeders;

import com.csumb.Administrative.entities.Teacher;
import com.csumb.Administrative.repositotries.ITeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TeacherSeeder  implements CommandLineRunner {
    @Autowired
    private ITeacherRepository teacherRepository;

    @Override
    public void run(String... args)throws  Exception {
        Teacher t1 = new Teacher("098","Ms. Gonzalez","English", 1);
        Teacher t2 = new Teacher("876", "Ms. Gurcha", "Science", 4);
        Teacher t3 = new Teacher("656", "Ms. Ramirez", "Art", 6);
        Teacher t4 = new Teacher("216", "Mr. Aguila", "Math", 3);

        teacherRepository.deleteAll();
        List<Teacher> teachers = Arrays.asList(t1,t2,t3,t4);
        teacherRepository.saveAll(teachers);
    }
}
