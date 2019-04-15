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

    GenerateController(){
        this.schedule = new HashMap<>();
        for(int i =1; i <= 6; i++){
            this.schedule.put(i,new ArrayList<>());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("generate")
    public void generate(){
        Map<Class,List<Student>> studentsToClasses = mapStudentToClasses();

        Iterator<Map.Entry<Class, List<Student>>> itr = studentsToClasses.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<Class, List<Student>> entry = itr.next();
            List<Section> sections;
            sections = createSections(entry.getKey(),entry.getValue().size());
            sections = setTeacherToSections(sections);
            setStudentToSections(sections, entry.getValue());
        }

    }

    public Map<Class,List<Student>> mapStudentToClasses(){
        Map<Class,List<Student>> studentsToClasses = new HashMap<>();
        Map<String, Class> classNameToClasses = new HashMap<>();
        List<Student> students = studentRepository.findAll();
        List<Class> classes = classRepository.findAll();

        for (Class c: classes) {
            if (!studentsToClasses.containsKey(c)) {
                studentsToClasses.put(c, new ArrayList<>());
                classNameToClasses.put(c.getClassName(),c);
            }
        }

        for (Student s: students) {
            for (String s_class : s.getPreferredClasses()) {
                Class aClass = classNameToClasses.get(s_class);
                studentsToClasses.get(aClass).add(s);
            }
        }
        return studentsToClasses;
    }

    public List<Section> createSections(Class c,int numStudents){
        List<Section> allSections = new ArrayList<>();
        int section_num = c.getMaxNumSections();

        for(int i = 1; i<= section_num; i++){
            allSections.add(new Section(c,i));
        }
        sectionRepository.saveAll(allSections);
        return allSections;
    }

    public List<Section> setTeacherToSections(List<Section> sections){
        String className = sections.get(0).getClassName();
            List<Teacher> teachers = teacherRepository.findAllByClassName(className);
            teachers.addAll(teacherRepository.findAllByClassName2(className));
            teachers.addAll(teacherRepository.findAllByClassName3(className));
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
            }else{
                i++;
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

    public List<Section> setStudentToSections(List<Section> sections, List<Student> students){

        String className = sections.get(0).getClassName();
        students = arrangeStudentPriority(students,className);

        for(Student s: students){
            int loc = findAvailableSection(sections, s);
            if(loc == -1){
                System.out.println(s);
            }else {
                sections.get(loc).addStudent(s);
                Section section = sections.get(loc);
                s.setPeriod(section.getPeriod_num(), section);
                Optional<Teacher> teacher = teacherRepository.findById(section.getTeacherID());
                if(teacher.isPresent()) {
                    teacher.get().updateCurrentNumStudents(1);
                    teacherRepository.save(teacher.get());
                }
                studentRepository.save(s);
            }
        }
        sectionRepository.saveAll(sections);
        return sections;
    }

    public int findAvailableSection(List<Section> sections, Student student){
        boolean canAdd;
        for(int i =0; i < sections.size(); i++){
            canAdd = true;
            Optional<Teacher> teacher = teacherRepository.findById(sections.get(i).getTeacherID());
            if(teacher.isPresent()){
                Teacher t = teacher.get();
                if(t.getMaxNumStudent() <= t.getCurrentNumStudent()){
                    canAdd = false;
                }
            }
            if(student.isPeriodAvailable(sections.get(i).getPeriod_num()) &&
                    sections.get(i).canAddStudent() && canAdd) {
                System.out.println("in here");
                return i;
            }
        }
        return -1;
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