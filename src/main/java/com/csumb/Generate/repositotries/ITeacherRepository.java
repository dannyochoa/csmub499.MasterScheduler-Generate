package com.csumb.Generate.repositotries;

import com.csumb.Generate.entities.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeacherRepository extends MongoRepository<Teacher,String> {

}
