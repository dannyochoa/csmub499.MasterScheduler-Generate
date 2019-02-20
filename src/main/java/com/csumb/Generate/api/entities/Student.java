package com.csumb.Generate.api.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Student {

    @Id
    String per_id;
    String name;
    int grade;
    List prefered_classes;

    public Student(String per_id, String name, int grade) {
        this.per_id = per_id;
        this.name = name;
        this.grade = grade;
    }

    public String getPer_id() {
        return per_id;
    }

    public void setPer_id(String per_id) {
        this.per_id = per_id;
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
}
