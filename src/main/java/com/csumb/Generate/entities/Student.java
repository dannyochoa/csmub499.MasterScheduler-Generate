package com.csumb.Generate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Student {

    @Id
    private String id;
    private String name;
    private int grade;
    private List<String> preferredClasses;
    private List<Boolean> preferred;
    private String academy;
    private List<String> schedule;

    public Student() {
        this.name ="";
        this.grade = 0;
        this.preferredClasses = new ArrayList<>();
        this.preferred = new ArrayList<>();
        this.academy = "";
        this.schedule = new ArrayList<>();
        for(int i =0; i < 6;i++){
            schedule.add("");
        }
    }

    public Student(String id, String name, int grade, String academy) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.academy = academy;
        this.preferredClasses = new ArrayList<>();
        this.preferred = new ArrayList<>();
        this.schedule = new ArrayList<>();
        for(int i =0; i < 6;i++){
            schedule.add("");
        }
    }

    public Student(String id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.academy = "";
        this.preferredClasses = new ArrayList<>();
        this.preferred = new ArrayList<>();
        this.schedule = new ArrayList<>();
        for(int i =0; i < 6;i++){
            schedule.add("");
        }
    }

    public Student(String id, String name, int grade,
                   String academy, List<String> preferred_classes,List<Boolean> preferred){
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.academy = academy;
        this.preferredClasses = preferred_classes;
        this.preferred = preferred;
        this.schedule = new ArrayList<>();
        for(int i =0; i < 6;i++){
            schedule.add("");
        }

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

    public List<String> getPreferredClasses() {
        return preferredClasses;
    }

    public void setPreferredClasses(List<String> preferred_classes) {
        this.preferredClasses = preferred_classes;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public boolean isClassPreferred(String className){
        for(int i =0; i < preferredClasses.size(); i++){
            if(preferredClasses.get(i).equals(className)){
                return preferred.get(i);
            }
        }
        return false;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }

    public void setPeriod(int time, Section section){
        System.out.println("here");
        schedule.set(time-1,section.getClassName());
    }

    public boolean isPeriodAvailable(int time){
        System.out.println(time-1);
        System.out.println("value " + schedule.get(time-1));
        return schedule.get(time-1).equals("");
    }

    public List<Boolean> getPreferred() {
        return preferred;
    }

    public void setPreferred(List<Boolean> preferred) {
        this.preferred = preferred;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", preferredClasses=" + preferredClasses +
                ", academy='" + academy + '\'' +
                ", schedule=" + schedule +
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
                Objects.equals(preferredClasses, student.preferredClasses) &&
                Objects.equals(academy, student.academy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, preferredClasses, academy);
    }
}
