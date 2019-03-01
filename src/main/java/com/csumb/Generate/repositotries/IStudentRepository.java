package com.csumb.Generate.repositotries;

<<<<<<< HEAD
=======

>>>>>>> 8080f6b28a3b4768d0dc85f7a22a719770b0d8e0
import com.csumb.Generate.entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends MongoRepository<Student,String> {

}

