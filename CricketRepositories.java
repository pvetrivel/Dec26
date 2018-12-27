package com.cricket.pavi.CricketDB.repositories;


import com.cricket.pavi.CricketDB.Cricket;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="team",collectionResourceRel="team")
public interface CricketRepositories extends MongoRepository<Cricket,String> {


    Cricket findBy_id(ObjectId _id);

}
