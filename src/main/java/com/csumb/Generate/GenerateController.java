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

import java.util.*;

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

    private Map<Integer, List<Section>> schedule;
    private int maxNumStudentPerSection;

    GenerateController(){
        this.maxNumStudentPerSection = 30;
        this.schedule = new HashMap<>();
        for(int i =1; i <= 6; i++){
            this.schedule.put(i,new ArrayList<>());
        }
    }

    @GetMapping("generate")
    public void generate(){
        List<Section> allSections = new ArrayList<>();
        Map<Class,List<Student>> studentsToClasses = mapStudentToClasses();

        Iterator<Map.Entry<Class, List<Student>>> itr = studentsToClasses.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<Class, List<Student>> entry = itr.next();
            List<Section> sections;
            sections = createSections(entry.getKey(),entry.getValue().size());
            sections = setTeacherToSections(sections);
            allSections.addAll(sections);
            setStudentsSchedule(sections, entry.getValue());
        }


    }

    public Map<Class,List<Student>> mapStudentToClasses(){
        Map<Class,List<Student>> studentsToClasses = new HashMap<>();
        List<Student> students = studentRepository.findAll();
        List<Class> classes = classRepository.findAll();

        for (Class c: classes) {
            if (!studentsToClasses.containsKey(c)) {
                studentsToClasses.put(c, new ArrayList<>());
            }
        }

        for (Student s: students) {
            for (Pair<Class, Boolean> student_c : s.getPreferred_classes()) {
                Class aClass = student_c.getFirst();
                studentsToClasses.get(aClass).add(s);
            }
        }
        return studentsToClasses;
    }

    public List<Section> createSections(Class c,int numStudents){
        List<Section> allSections = new ArrayList<>();
        int section_num = numStudents/maxNumStudentPerSection;
        for(int i = 1; i<= section_num; i++){
            allSections.add(new Section(c,i));
        }
        return allSections;
    }

    public List<Section> setTeacherToSections(List<Section> sections){
        String className = sections.get(0).getClassName();
        List<Teacher> teachers = teacherRepository.findAllByClassName(className);
        int teacherIndex = 0;
        int i =0;
        while(i < sections.size() && teacherIndex < teachers.size()) {
            if(sections.get(i).getTeacherID().equals("")){
                if(teachers.get(teacherIndex).getSections().size() <
                    teachers.get(teacherIndex).getMaxNumSections()) {
                    sections.get(i).setTeacherID(teachers.get(teacherIndex).getId());
                    teachers.get(teacherIndex).addSection(sections.get(i));
                    i++;
                }else{
                    int prep = setTimeForSections(teachers.get(teacherIndex).getSections(),
                            teachers.get(teacherIndex).getId());
                    teachers.get(teacherIndex).setPrep(prep);
                    teacherIndex++;
                }
            }
        }
        setTimeForSections(sections,null);
        teacherRepository.saveAll(teachers);
        return sections;
    }

    public int findMinSection(){
        int min = schedule.get(1).size();
        int minLoc = 1;
        for(int i = 2; i <= 6; i++){
            if(min > schedule.get(i).size()){
                minLoc = i;
                min = schedule.get(i).size();
            }
        }
        return minLoc;
    }

    public int setTimeForSections(List<Section> sections, String id){
        List<Integer> prepPeriod = Arrays.asList(1,2,3,4,5,6);
        int index;
        for(int i =0; i < sections.size(); i++){
            if(sections.get(i).getPeriod_num() == -1) {
                index = findMinSection();
                sections.get(i).setPeriod_num(index);
                schedule.get(index).add(sections.get(i));
                if(id != null)
                    prepPeriod.set(index-1,-1);
            }
        }
        for(int i =0; i < 6;i++) {
            if (prepPeriod.get(i) != -1)
                return prepPeriod.get(i);
        }
        return -1;
    }

    public List<Section> setStudentToSections(List<Student> students, List<Section> sections){
        //separate students
        List<Student> fastStudents =  new ArrayList<>();
        List<Student> greenStudents =  new ArrayList<>();
        List<Student> normalStudents = new ArrayList<>();

        for(int i =0; i < students.size(); i++){
            if(students.get(i).getAcademy().equals("fast")){
                fastStudents.add(students.get(i));
            }
            else if(students.get(i).getAcademy().equals("green")){
                greenStudents.add(students.get(i));
            }
            else{
                normalStudents.add(students.get(i));
            }
        }

        int sectionIndex = 0;

        if(!fastStudents.isEmpty()) {
//            proposeClass(sections)
            sections.get(sectionIndex).setStudents(fastStudents);
            sectionIndex++;
        }
        if(!greenStudents.isEmpty()) {
            sections.get(sectionIndex).setStudents(greenStudents);
            sectionIndex++;
        }

        String className = sections.get(0).getClassName();

        normalStudents = arrangeStudentPriority(normalStudents,className);
        //refactor this with stable marriage algorithm
        int studentIndex = 0;
        for(int i = sectionIndex; i < sections.size(); i++){
            sections.get(i).setStudents(normalStudents.subList(studentIndex, studentIndex + 30));
            studentIndex+=30;
        }
        return sections;
    }

    public void setStudentsSchedule(List<Section> sections, List<Student> students){

    }

    public List<Student> arrangeStudentPriority(List<Student> students, String className){
        List<Student> tempStudents = new ArrayList<>();
        for(Student s: students){
            if(s.isClassPreferred(className)){
                tempStudents.add(0,s);
            }else{
                tempStudents.add(s);
            }
        }
        return tempStudents;
    }
}