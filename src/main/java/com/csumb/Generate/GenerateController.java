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
            //setSections(k, v);
        });


        return null;
    }

    public List<Section> setSections(Class c, List<Student> students){
        List<Student> tempstudent = new ArrayList<>();
        List<Section> allSections = new ArrayList<>();
        int section_num = 1;

        while(!students.isEmpty()){

            //if there are 30 students or less students then create new section right away
            // What is the max number of students in a class?
            // dismiss the ones that do not fit into groups of 30?
            //minimum number of students in a class? 
            //check inititially if there is well divided groups _> increase number of students for each section?
            if(students.size() <= 30 ){
                tempstudent.addAll(students);
                //if left over students then ask user if wanting to creat new section
                
            }else {
                //divide students into groups of 30
                for(int i =0; i < 30; i++){
                    tempstudent.add(students.get(i));
                    students.remove(i);
                }
            }
            
            //set class, section number and group of students
            allSections.add(new Section(c,section_num,tempstudent));
            section_num++;
        }

        for(Section s: allSections){
            System.out.println("Class:" + s.getClassName() + "  Students: ");
            for(Student student: s.getStudents()){
                System.out.println(student.getId() + " " + student.getName());
            }
        }
        
        return null;
    }

}