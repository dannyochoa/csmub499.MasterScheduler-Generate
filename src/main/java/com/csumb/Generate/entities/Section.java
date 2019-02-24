package com.csumb.Generate.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Section extends Class{

    private int section_num;
    private int period_num;
    private List<Student> students;

    public Section(Class c, int section_num){
        super(c);
        this.class_id = this.class_id + "_" + section_num;
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

    @Override
    public String toString() {
        return "Section{" +
                "id='" + class_id + '\'' +
                ", section_num=" + section_num +
                ", perioud_num=" + period_num +
                ", students=" + students +
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
                Objects.equals(students, section.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), section_num, period_num, students);
    }
}
