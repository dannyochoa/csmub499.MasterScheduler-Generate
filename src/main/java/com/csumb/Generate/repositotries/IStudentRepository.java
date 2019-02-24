package com.csumb.Generate.repositotries;

import com.csumb.Generate.entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends MongoRepository<Student,String> {

//    @Query(value = "{id: $id}", delete = true)
//    void deleteById(String id);
}

