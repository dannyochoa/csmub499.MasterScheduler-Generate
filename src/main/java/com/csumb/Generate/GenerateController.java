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

    private static boolean isGenerated = false;

    GenerateController(){
        this.schedule = new HashMap<>();
        for(int i =1; i <= 6; i++){
            this.schedule.put(i,new ArrayList<>());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("generate")
    public void generate(){
        isGenerated = true;
        Map<Class,List<Student>> studentsToClasses = mapStudentToClasses();

        Iterator<Map.Entry<Class, List<Student>>> itr = studentsToClasses.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<Class, List<Student>> entry = itr.next();
            List<Section> sections;
            sections = createSections(entry.getKey(),entry.getValue().size());
            sections = setTimeForSections(sections);
            sections = setTeacherToSections(sections);
            sections = setStudentToSections(sections, entry.getValue());
            sectionRepository.saveAll(sections);
        }

        List<Teacher> teachers = teacherRepository.findAll();
        for(Teacher t: teachers){
            t.setPrep();
            t.sortSection();
        }
        teacherRepository.saveAll(teachers);
//        List<Section> sec = sectionRepository.findAll();
//        for(Section s: sec){
//            System.out.println(s.getClassName() + " " + s.getRoster().size() + " " + s.getTeacherID());
//        }
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

        int section_num = numStudents/c.getMaxNumStudentPerSection();
        if(section_num > c.getMaxNumSections())
            section_num = c.getMaxNumSections();
        if(section_num == 0)
            section_num = 1;
//        System.out.println("section num " + section_num);
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

        int i =0;
        while(i < sections.size()) {
            if (sections.get(i).getTeacherID().equals("")) {
                for (Teacher t : teachers) {
                    if (t.getSections().size() < t.getMaxNumSections() && t.getCurrentNumStudent() < t.getMaxNumStudent()
                            && t.canAddSection(sections.get(i).getPeriodNum())) {
                        sections.get(i).setTeacherID(t.getId());
                        t.addSection(sections.get(i));
                        break;
                    }
                }
            }
            i++;
        }
//                if(teachers.get(teacherIndex).getSections().size() <
//                    teachers.get(teacherIndex).getMaxNumSections() &&
//                    teachers.get(teacherIndex).getCurrentNumStudent() <
//                    teachers.get(teacherIndex).getMaxNumStudent()) {
//                    System.out.println("adding a section");
//                    sections.get(i).setTeacherID(teachers.get(teacherIndex).getId());
//                    teachers.get(teacherIndex).addSection(sections.get(i));
//                    teachers.get(teacherIndex).sortSection();
//                    i++;
//                    System.out.println("here");
//                }
//                else{
//                    int prep = setTimeForSections(teachers.get(teacherIndex).getSections(),
//                            teachers.get(teacherIndex).getId());
//                    System.out.println("in prep " + prep) ;
//                    teachers.get(teacherIndex).setPrep(prep);
//                    teachers.get(teacherIndex).sortSection();
//                    teacherIndex++;
//                }
//            }else{
//                i++;
//            }
//        }
//        setTimeForSections(sections,null);
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

    public List<Section> setTimeForSections(List<Section> sections){
        int index;
        for(int i =0; i < sections.size(); i++){
            if(sections.get(i).getPeriodNum() == -1) {
                index = findMinSection();
                sections.get(i).setPeriodNum(index);
                schedule.get(index).add(sections.get(i));
            } else{
                schedule.get(sections.get(i).getPeriodNum()).add(sections.get(i));
            }
        }
        return sections;
    }

    public List<Section> setStudentToSections(List<Section> sections, List<Student> students){

        String className = sections.get(0).getClassName();
        students = arrangeStudentPriority(students,className);

        for(Student s: students){
            int loc = findAvailableSection(sections, s);
            if(loc == -1){
                System.out.println("Cannot add student");
            }else {
                System.out.println("adding student");
                Section section = sections.get(loc);
                int numStudents = section.getRoster().size();
                s.setPeriod(section.getPeriodNum(), section);
                section.addStudent(s);
                Optional<Teacher> teacher = teacherRepository.findById(section.getTeacherID());
                if (teacher.isPresent()) {
                    teacher.get().updateCurrentNumStudents(1);
                    teacherRepository.save(teacher.get());
                }
                studentRepository.save(s);
                sectionRepository.save(section);
            }
        }
//        sectionRepository.saveAll(sections);
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
                    System.out.println("can add is false");
                    canAdd = false;
                }
            }
            if(student.isPeriodAvailable(sections.get(i).getPeriodNum()) &&
                    sections.get(i).canAddStudent() && canAdd) {
                System.out.println("cannot add in here");
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

    @CrossOrigin(origins = "*")
    @GetMapping("remove")
    public boolean remove() {
        isGenerated = false;
        return false;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("isgenerated")
    public boolean isGenerate(){
        return isGenerated;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getschedule")
    public List<List<Teacher>> getSchedule(){
        if(!isGenerated)
            return null;
        List<Teacher> teachers = teacherRepository.findAll();
        List<List<Teacher>> ans = new ArrayList<>();
        HashMap<String, Integer> index = new HashMap<>();
        int i = 0;
        String department;
        for(Teacher t: teachers){
            department = t.getDepartment();
            if(index.containsKey(department)){
                List<Teacher> temp = ans.get(index.get(department));
                temp.add(t);
                ans.set(index.get(department), temp);
            }else{
                index.put(department,i);
                i++;
                ans.add(new ArrayList<>());
                List<Teacher> temp = ans.get(index.get(department));
                temp.add(t);
                ans.set(index.get(department), temp);
            }
        }
        return ans;
    }

}