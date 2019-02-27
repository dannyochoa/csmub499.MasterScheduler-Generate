package com.csumb.Generate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Class {

    @Id
    protected String class_id;
    protected String department;
    protected String className;
    protected String classRoom;
    protected String teacherId;

    public Class(Class c){
        this.class_id = c.getClass_id();
        this.department = c.getDepartment();
        this.className = c.getClassName();
        this.classRoom = c.getClassRoom();
        this.teacherId = c.getTeacherId();
    }

    public Class(String department, String className) {
        this.department = department;
        this.className = className;
    }

    public Class(String id, String department, String className) {
        this.class_id = id;
        this.department = department;
        this.className = className;
    }

    public Class(String class_id, String department, String className, String classRoom, String teacherId) {
        this.class_id = class_id;
        this.department = department;
        this.className = className;
        this.classRoom = classRoom;
        this.teacherId = teacherId;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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
                ", teacherId='" + teacherId + '\'' +
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
                Objects.equals(classRoom, aClass.classRoom) &&
                Objects.equals(teacherId, aClass.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(class_id, department, className, classRoom, teacherId);
    }
}
