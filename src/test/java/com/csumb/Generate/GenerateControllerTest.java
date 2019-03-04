package com.csumb.Generate;

import com.csumb.Generate.entities.Student;
import com.csumb.Generate.repositotries.IClassRepository;
import com.csumb.Generate.repositotries.ISectionRepository;
import com.csumb.Generate.repositotries.IStudentRepository;
import com.csumb.Generate.repositotries.ITeacherRepository;
import com.csumb.Generate.entities.Class;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


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
    private Class woodShop = new Class("Elective", "Wood shop","6");
    private Class drafting = new Class("Elective", "Drafting","7");
    private Class french = new Class("Language","French 1","8");
    private Class spanish = new Class("Language","spanish 1","9");

    private List<Class> classData = Arrays.asList(woodShop, drafting, french,history, spanish,bio, pe,algebra, english9);
    private List<Class> electiveData = Arrays.asList(woodShop, drafting, french,history, spanish,bio);
    private List<Student> studentData = new ArrayList<>();

    private List<Pair<Class, Boolean>> setClasses(){
        Random rand = new Random();
        List<Pair<Class,Boolean>> classes = new ArrayList<>();
        for(int i =0; i < 3; i++){
            int n = rand.nextInt(6);
            if(!classes.contains(Pair.of(electiveData.get(n), false)))
                classes.add(Pair.of(electiveData.get(n), false));
            else{
                i--;
            }
        }
        return classes;
    }

    private void setStudentData(){
        for(int i =0; i < 3000; i++){
            Student temp = new Student("123" + i, "student_" + i,10 );
            ArrayList<Pair<Class,Boolean>> tempClasses = new ArrayList<>();
            tempClasses.addAll(Arrays.asList(Pair.of(english9,false), Pair.of(pe,false), Pair.of(algebra, false)));

            tempClasses.addAll(setClasses());
            temp.setPreferred_classes(tempClasses);

            studentData.add(temp);
        }
    }


    @Test
    public void updateStudents() {
        setStudentData();
        when(studentRepository.findAll()).thenReturn(studentData);
        when(classRepository.findAll()).thenReturn(classData);
        generateController.updateStudents();
    }
}
