package com.csumb.Generate.repositotries;

import com.csumb.Generate.entities.Section;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectionRepository  extends MongoRepository<Section,String> {

}
