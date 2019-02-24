package com.csumb.Generate.repositotries;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.csumb.Generate.entities.Student;

@Repository
public interface IStudentRepository extends MongoRepository<Student,String> {

//    @Query(value = "{id: $id}", delete = true)
//    void deleteById(String id);
}

