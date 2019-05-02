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
    private List<String> scheduleId;

    public Student() {
        this.name = "";
        this.grade = 0;
        this.preferredClasses = new ArrayList<>();
        this.preferred = new ArrayList<>();
        this.academy = "";
        this.schedule = new ArrayList<>();
        this.scheduleId = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            schedule.add("");
            scheduleId.add("");
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
        this.scheduleId = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            schedule.add("");
            scheduleId.add("");
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
        this.scheduleId = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            schedule.add("");
            scheduleId.add("");
        }
    }

    public Student(String id, String name, int grade,
                   String academy, List<String> preferred_classes, List<Boolean> preferred) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.academy = academy;
        this.preferredClasses = preferred_classes;
        this.preferred = preferred;
        this.schedule = new ArrayList<>();
        this.scheduleId = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            schedule.add("");
            scheduleId.add("");
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

    public boolean isClassPreferred(String className) {
        for (int i = 0; i < preferredClasses.size(); i++) {
            if (preferredClasses.get(i).equals(className)) {
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

    public void setPeriod(int time, Section section) {
        System.out.println("setting student schedule");
        schedule.set(time - 1, section.getClassName());
        scheduleId.set(time - 1, section.getId());
    }

    public boolean isPeriodAvailable(int time) {
        if(!schedule.get(time - 1).equals(""))
            System.out.println("spot is taken" + schedule.get(time-1));
        return schedule.get(time - 1).equals("");
    }

    public List<Boolean> getPreferred() {
        return preferred;
    }

    public void setPreferred(List<Boolean> preferred) {
        this.preferred = preferred;
    }

    public List<String> getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(List<String> scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<String> getData() {
        List<String> ans = new ArrayList<>();
        ans.add(this.id);
        ans.add(this.name);
        ans.add(Integer.toString(this.grade));
        ans.add(this.academy);
        for (int i = 0; i < 6; i++) {
            if (this.schedule.get(i).equals("")) {
                ans.add("Please Generate");
            } else {
                ans.add(this.schedule.get(i));
            }
        }
        for (int i = 0; i < 6; i++) {
            ans.add(preferredClasses.get(i));
            ans.add(Boolean.toString(preferred.get(i)));
        }
        return ans;
    }

    public void setScheduleSection(Section s) {
        schedule.add(s.getClassName());
    }

    public void removeScheduleSection(Section s) {
        schedule.remove(s.getClassName());
        setSchedule(schedule);
        System.out.println("here");

    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", preferredClasses=" + preferredClasses +
                ", preferred=" + preferred +
                ", academy='" + academy + '\'' +
                ", schedule=" + schedule +
                ", scheduleId=" + scheduleId +
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
                preferredClasses.equals(student.preferredClasses) &&
                preferred.equals(student.preferred) &&
                academy.equals(student.academy) &&
                schedule.equals(student.schedule) &&
                scheduleId.equals(student.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, preferredClasses, preferred, academy, schedule, scheduleId);
    }
}