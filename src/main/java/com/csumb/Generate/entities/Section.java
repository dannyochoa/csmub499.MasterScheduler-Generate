package com.csumb.Generate.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Section extends Class {

    private int section_num;
    private int period_num;
    private List<Student> students;
    private String TeacherID;

    public Section( ){

    }

    public Section(String department, String className, String classRoom, int section_num, int period_num, List<Student> students, String teacherID) {
        super(department, className, classRoom);
        this.section_num = section_num;
        this.period_num = period_num;
        this.students = students;
        TeacherID = teacherID;
    }

    public Section(Class c, int section_num, int period_num, List<Student> students, String teacherID) {
        super(c);
        this.section_num = section_num;
        this.period_num = period_num;
        this.students = students;
        TeacherID = teacherID;
    }

    public Section(Class c, int section_num) {
        super(c);
        this.setClass_id(this.getClass_id() + "_" + section_num);
        this.section_num = section_num;
    }

    public int getSection_num() {
        return section_num;
    }

    public void setSection_num(int section_num) {
        this.section_num = section_num;
    }

    public int getPeriod_num() {
        return period_num;
    }

    public void setPeriod_num(int period_num) {
        this.period_num = period_num;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    @Override
    public String toString() {
        return "Section{" +
                "section_num=" + section_num +
                ", period_num=" + period_num +
                ", students=" + students +
                ", TeacherID='" + TeacherID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        if (!super.equals(o)) return false;
        Section section = (Section) o;
        return section_num == section.section_num &&
                period_num == section.period_num &&
                Objects.equals(students, section.students) &&
                Objects.equals(TeacherID, section.TeacherID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), section_num, period_num, students, TeacherID);
    }
}