package com.csumb.Generate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Class {

    @Id
    private String class_id;
    private String department;
    private String className;
    private String classRoom;

    public Class() {
        this.department = "";
        this.className = "";
        this.classRoom = "";
    }

    public Class(String department, String className) {
        this.department = department;
        this.className = className;
    }

    public Class(String department, String className, String id) {
        this.department = department;
        this.className = className;
        this.class_id = id;
    }


    public Class(Class c) {
        this.class_id = c.getClass_id();
        this.department = c.getDepartment();
        this.className = c.getClassName();
        this.classRoom = c.getClassRoom();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "Class{" +
                "class_id='" + class_id + '\'' +
                ", department='" + department + '\'' +
                ", className='" + className + '\'' +
                ", classRoom='" + classRoom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Class)) return false;
        Class aClass = (Class) o;
        return class_id.equals(aClass.class_id) &&
                department.equals(aClass.department) &&
                className.equals(aClass.className) &&
                classRoom.equals(aClass.classRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(class_id, department, className, classRoom);
    }
}