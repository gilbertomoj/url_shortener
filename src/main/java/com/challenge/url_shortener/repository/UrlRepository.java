package com.challenge.url_shortener.repository;

import com.challenge.url_shortener.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {

    Url findByAccessUrl(String accessUrl);
}
