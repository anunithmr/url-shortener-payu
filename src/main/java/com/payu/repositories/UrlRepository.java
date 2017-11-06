package com.payu.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payu.models.Urls;

@Repository
public interface UrlRepository extends CrudRepository<Urls,String>{

}
