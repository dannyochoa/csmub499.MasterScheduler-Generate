package com.csumb.Generate.repositotries;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassRepository extends MongoRepository<Class,String> {

}
