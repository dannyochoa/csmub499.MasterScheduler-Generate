package com.csumb.Generate.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Section extends Class {

    private int section_num;
    private int period_num;
    private List<Pair<String, String>> roster;
    private String teacherID;
    private int maxStudent;

    public Section( ){
        this.roster = new ArrayList<>();
    }

    public Section(String department, String className, String classRoom, int section_num, int period_num, List<Pair<String, String>> students, String teacherID) {
        super(department, className, classRoom);
        this.section_num = section_num;
        this.period_num = period_num;
        this.roster = students;
        this.teacherID = teacherID;
        this.maxStudent = 30;
    }

    public Section(Class c, int section_num, int period_num, List<Pair<String, String>> students, String teacherID) {
        super(c);
        this.section_num = section_num;
        this.period_num = period_num;
        this.roster = students;
        this.teacherID = teacherID;
        this.maxStudent = 30;
    }

    public Section(Class c, int section_num) {
        super(c);
        this.setId(this.getId() + "_" + section_num);
        this.section_num = section_num;
        this.teacherID = "";
        this.period_num = -1;
        this.maxStudent = 30;
        this.roster = new ArrayList<>();
    }

    public Section(Class c, int section_num, List<Pair<String, String>> students) {
        super(c);
        this.setId(this.getId() + "_" + section_num);
        this.section_num = section_num;
        this.roster = students;
        this.period_num = -1;
        this.maxStudent = 30;
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

    public List<Pair<String, String>> getRoster() {
        return roster;
    }

    public void setRoster(List<Pair<String, String>> roster) {
        this.roster = roster;
    }

    public void addStudent(Student student) {
        this.roster.add(Pair.of(student.getId(),student.getName()));
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public int getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }

    public boolean canAddStudent(){
        return maxStudent >= roster.size()+1;
    }
    @Override
    public String toString() {
        return "Section{" +
                "section_num=" + section_num +
                ", period_num=" + period_num +
                ", students=" + roster +
                ", teacherID='" + teacherID + '\'' +
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
                Objects.equals(roster, section.roster) &&
                Objects.equals(teacherID, section.teacherID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), section_num, period_num, roster, teacherID);
    }
}