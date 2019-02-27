package com.csumb.Generate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Student {

    @Id
    String id;
    String name;
    int grade;
    List prefered_classes;

    public Student() {
        this.grade = 0;
        this.name = "test";
        this.id = "00000";
    }

    public Student(String id) {
        this.id = id;
    }

    public Student(String id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
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

    public List getprefered_classes() {
        return prefered_classes;
    }

    public void setprefered_classes(List prefered_classes) {
        this.prefered_classes = prefered_classes;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", prefered_classes=" + prefered_classes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return grade == student.grade &&
                id.equals(student.id) &&
                name.equals(student.name) &&
                prefered_classes.equals(student.prefered_classes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, prefered_classes);
    }
}
