package com.csumb.Generate;

import com.csumb.Generate.entities.Section;
import com.csumb.Generate.entities.Student;
import com.csumb.Generate.entities.Teacher;
import com.csumb.Generate.repositotries.IClassRepository;
import com.csumb.Generate.repositotries.ISectionRepository;
import com.csumb.Generate.repositotries.IStudentRepository;
import com.csumb.Generate.repositotries.ITeacherRepository;
import com.csumb.Generate.entities.Class;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateControllerTest {

    @Autowired
    private GenerateController generateController;

    @MockBean
    private IClassRepository classRepository;

    @MockBean
    private ISectionRepository sectionRepository;

    @MockBean
    private IStudentRepository studentRepository;

    @MockBean
    private ITeacherRepository teacherRepository;

    private Class algebra = new Class("Math", "Algebra","1");
    private Class english9 = new Class("English", "Frosh English","2");
    private Class pe = new Class("PE", "Physical Education","3");
    private Class history = new Class("Social Science", "History","4");
    private Class bio = new Class("Science", "Biology","5");
    private List<Class> classData = Arrays.asList(history, bio, pe,algebra, english9);

    private List<Teacher> teacherData = Arrays.asList(new Teacher("123","Judith","Social Science","History"));

    private List<Section> sectionData = new ArrayList<>();
    private List<Student> studentData = new ArrayList<>();

    private void setStudentData(){
        List<Pair<Class, Boolean>> classes = new ArrayList<>();
        for(int i=0; i < classData.size();i++){
            classes.add(Pair.of(classData.get(i),false));
        }

        List<Pair<Class, Boolean>> priorityClasses = new ArrayList<>();
        for(int k=0; k < classData.size();k++){
            priorityClasses.add(Pair.of(classData.get(k),true));
        }
        for(int i=0; i < 210;i++){
            Student studentToAdd = new Student("123_"+i, "student_"+i,9);
            studentToAdd.setPreferred_classes(classes);
            if(i < 30)
                studentToAdd.setAcademy("fast");
            if(i < 60 && i >= 30)
                studentToAdd.setAcademy("green");
            if(i == 105  || i == 106 || i == 107){
                studentToAdd.setPreferred_classes(priorityClasses);
            }
            studentData.add(studentToAdd);
        }
    }

    private void setSections(){
        for(int i=1; i <= studentData.size()/30 ; i++){
            sectionData.add(new Section(classData.get(0),i));
        }
    }

    @Test
    public void mapStudentToClasses(){
        setStudentData();
        when(studentRepository.findAll()).thenReturn(studentData);
        when(classRepository.findAll()).thenReturn(classData);
        Map<Class,List<Student>> response = generateController.mapStudentToClasses();
        Map<Class,List<Student>> expected = new HashMap<>();
        for(int i =0; i< classData.size();i++){
            expected.put(classData.get(i),studentData);
        }
        Assert.assertEquals(expected,response);
    }


    @Test
    public void createSections (){
        setStudentData();
        setSections();
        List<Section> response = generateController.createSections(classData.get(0),studentData.size());
        Assert.assertEquals(sectionData,response);
    }

    @Test
    public void setStudentToSections(){
        setStudentData();
        setSections();
        List<Section> response = generateController.setStudentToSections(studentData,sectionData);
        Assert.assertEquals(sectionData,response);
    }

    @Test
    public void setTeacherToSections(){
        setStudentToSections();
        when(teacherRepository.findAllByClassName(sectionData.get(0).getClassName())).thenReturn(teacherData);
        List<Section> response = generateController.setTeacherToSections(sectionData);
        Assert.assertEquals(sectionData, response);

        System.out.println(sectionData.get(0));
        System.out.println(sectionData.get(1));
        System.out.println(sectionData.get(2));
        System.out.println(sectionData.get(3));
        System.out.println(sectionData.get(4));
        System.out.println(sectionData.get(5));
        System.out.println(sectionData.get(6));
        System.out.println(teacherData.get(0));
   }


}
