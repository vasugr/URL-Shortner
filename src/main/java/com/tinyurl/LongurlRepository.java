package com.tinyurl;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LongurlRepository extends MongoRepository<Longurl,String> {
    
}
