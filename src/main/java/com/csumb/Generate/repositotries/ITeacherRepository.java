package com.csumb.Generate.repositotries;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.csumb.Generate.entities.Teacher;

@Repository
public interface ITeacherRepository extends MongoRepository<Teacher,String> {

}
