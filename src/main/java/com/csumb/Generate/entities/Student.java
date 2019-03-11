package com.csumb.Generate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Student {

    @Id
    private String id;
    private String name;
    private int grade;
    private List<Pair<Class, Boolean>> preferred_classes;
    private String academy;

    public Student() {
        this.name ="";
        this.grade = 0;
        this.preferred_classes = new ArrayList<>();
        this.academy = "";
    }

    public Student(String id, String name, int grade, String academy) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.academy = academy;
    }

    public Student(String id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.academy = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Pair<Class, Boolean>> getPreferred_classes() {
        return preferred_classes;
    }

    public void setPreferred_classes(List<Pair<Class, Boolean>> preferred_classes) {
        this.preferred_classes = preferred_classes;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", preferred_classes=" + preferred_classes +
                ", academy='" + academy + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return grade == student.grade &&
                id.equals(student.id) &&
                name.equals(student.name) &&
                Objects.equals(preferred_classes, student.preferred_classes) &&
                Objects.equals(academy, student.academy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, preferred_classes, academy);
    }
}
