package com.csumb.Generate.repositotries;

import com.csumb.Generate.entities.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITeacherRepository extends MongoRepository<Teacher, String> {

    List<Teacher> findAllByDepartment(String department);

    List<Teacher> findAllByClassName(String className);
    List<Teacher> findAllByClassName2(String className);
    List<Teacher> findAllByClassName3(String className);
}
