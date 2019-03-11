package com.csumb.Generate;

import com.csumb.Generate.entities.Class;
import com.csumb.Generate.entities.Section;
import com.csumb.Generate.entities.Student;
import com.csumb.Generate.entities.Teacher;
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

    public Map<Class,List<Student>> mapStudentToClasses(){
        Map<Class,List<Student>> studentsToClasses = new HashMap<>();
        List<Student> students = studentRepository.findAll();
        List<Class> classes = classRepository.findAll();
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
        return studentsToClasses;
    }

    public void createSectionsForClasses(Map<Class,List<Student>> studentsToClasses){
        studentsToClasses.forEach((c,s) -> {
            List<Section> sections;
            sections = createSections(c,s.size());
            sections = setStudentToSections(s,sections);
            sections = setTeacherToSections(sections);
        });
    }

    public List<Section> createSections(Class c,int numStudents){
        List<Section> allSections = new ArrayList<>();
        int section_num = numStudents/30;
        for(int i = 1; i<= section_num; i++){
            allSections.add(new Section(c,i));
        }
        System.out.println(section_num);
        return allSections;
    }

    public List<Section> setStudentToSections(List<Student> students, List<Section> sections){
        //separate students
        List<Student> fastStudents =  new ArrayList<>();
        List<Student> greenStudents =  new ArrayList<>();

        for(int i =0; i < students.size();i++){
            if(students.get(i).getAcademy() == "fast"){
                fastStudents.add(students.get(i));
                students.remove(i);
            }
            if(students.get(i).getAcademy() == "green"){
                greenStudents.add(students.get(i));
                students.remove(i);
            }
        }

        int sectionIndex = 0;
        if(!fastStudents.isEmpty()) {
            sections.get(sectionIndex).setStudents(fastStudents);
            sectionIndex++;
        }
        if(!greenStudents.isEmpty()) {
            sections.get(sectionIndex).setStudents(greenStudents);
            sectionIndex++;
        }

        int studentIndex = 0;
        for(int i = sectionIndex; i < sections.size(); i++){
            sections.get(i).setStudents(students.subList(studentIndex, studentIndex + 30));
            studentIndex+=30;
        }
        return sections;
    }

    @GetMapping("/teachersByClassName/{className}")
    public List<Teacher> findByClassName(@PathVariable String className){
        return teacherRepository.findAllByClassName(className);
    }

    public List<Section> setTeacherToSections(List<Section> sections){
        String className = sections.get(0).getClassName();
        List<Teacher> teachers = teacherRepository.findAllByClassName(className);
        int teacherIndex = 0;
        int i =0;
        while(i < sections.size()) {
            Section s = sections.get(i);
            if(s.getTeacherID() == ""){
                if(teachers.get(teacherIndex).getCurrentNumStudent() + s.getStudents().size() <
                        teachers.get(teacherIndex).getMaxNumStudent()) {
                    s.setTeacherID(teachers.get(teacherIndex).getId());
                    teachers.get(teacherIndex).addClass(s);
                    i++;
                } else{
                  teacherIndex++;
                }
            }
        }
        teacherRepository.saveAll(teachers);
        return sections;
    }
}