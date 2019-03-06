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
    public List<Section> generateSections(){
        List<Student> students = studentRepository.findAll();
        List<Class> classes = classRepository.findAll();
        //map students who want to take a class
        Map<Class,List<Student>> studentsToClasses = new HashMap<>();

        List<Student> tempStudents;

        for (Class c: classes) {
            if (!studentsToClasses.containsKey(c)) {
                studentsToClasses.put(c, new ArrayList<>());
            }
        }
        for (Student s: students) {
            for (Pair<Class, Boolean> student_c : s.getPreferred_classes()) {
                Class tempClass = student_c.getFirst();
                tempStudents = studentsToClasses.get(tempClass);
                tempStudents.add(s);
                studentsToClasses.remove(tempClass);
                studentsToClasses.put(tempClass, tempStudents);
            }
        }

        studentsToClasses.forEach((k,v) -> {
            System.out.println(k.getClassName() + " num student want to take " + v.size() +
                    "num sections " + v.size()/30);
            //
            //
            setSections(k, v);
        });

        return null;
    }


    public List<Section> setSections(Class c, List<Student> students){
        List<Student> tempStudent;
        List<Section> allSections = new ArrayList<>();
        int section_num = students.size()/30;
        int j = 0;
        for(int i = 1; i<= section_num; i++){
            //set class, section number and group of students
            tempStudent = students.subList(j,j+30);
            allSections.add(new Section(c,i,tempStudent));
            j+=30;
        }

        for(Section s: allSections) {
            System.out.println("Class:" + s.getClassName() + "  Section num: " + s.getSection_num()
                    + " num s: " + s.getStudents().size());
        }
        return null;
    }

    public List<Section> setSections2(Class c, List<Student> students){
        List<Student> tempStudent;
        List<Section> allSections = new ArrayList<>();
        List<List<Section>> teacherSchedule = new ArrayList<>(new ArrayList<>());
        int section_num = students.size()/30;
        int techersNeeded = students.size()/160;
        int j = 0;

        for(int i = 1; i<= section_num; i++){
            //set class, section number and group of students
            tempStudent = students.subList(j,j+30);
            allSections.add(new Section(c,i,tempStudent));
            j+=30;
        }

        //separate teachers to sections
        teacherRepository.findAll();

        for (){

        }

        for(Section s: allSections) {
            System.out.println("Class:" + s.getClassName() + "  Section num: " + s.getSection_num()
                    + " num s: " + s.getStudents().size());
        }

        return null;
    }
}