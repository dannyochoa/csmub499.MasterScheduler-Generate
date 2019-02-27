package com.csumb.Generate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Teacher {

    @Id
    String id;
    String name;
    String department;
    int prep;

    public Teacher(String id, String name, String department, int prep) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.prep = prep;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPrep() {
        return prep;
    }

    public void setPrep(int prep) {
        this.prep = prep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return prep == teacher.prep &&
                id.equals(teacher.id) &&
                name.equals(teacher.name) &&
                department.equals(teacher.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, department, prep);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", prep=" + prep +
                '}';
    }
}
