package com.csumb.Generate;

import com.csumb.Generate.entities.Class;
import com.csumb.Generate.entities.Section;
import com.csumb.Generate.entities.Student;
import com.csumb.Generate.repositotries.IClassRepository;
import com.csumb.Generate.repositotries.ISectionRepository;
import com.csumb.Generate.repositotries.IStudentRepository;
import com.csumb.Generate.repositotries.ITeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.util.Pair;

@RestController
public class GenerateController {
    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IClassRepository classRepository;

    @Autowired
    private ITeacherRepository teacherRepository;

    @Autowired
    private ISectionRepository sectionRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/generatesections")
    public List<Section> updateStudents(){
        List<Student> students = studentRepository.findAll();
        List<Class> classes = classRepository.findAll();
        //map students who want to take a class
        Map<Class,List<Student>> studentsToClasses = new HashMap<>();

        List<Student> tempStudents;

        for (Class c: classes) {
            if(!studentsToClasses.containsKey(c)) {
                studentsToClasses.put(c, new ArrayList<>());
            }
            if(studentsToClasses.containsKey(c)){
                tempStudents = studentsToClasses.get(c);
                for (Student s: students) {
                    for(Pair<Class, Boolean> student_c: s.getPreferred_classes()){
                        if(student_c.getFirst()== c)
                            tempStudents.add(s);
                    }
                }
                studentsToClasses.remove(c);
                studentsToClasses.put(c, tempStudents);
            }
        }

        studentsToClasses.forEach((k,v) -> {
            System.out.println(k.getClassName() + " num student want to take " + v.size() +
                    "num sections " + v.size()/30);
        });


        return null;
    }

}