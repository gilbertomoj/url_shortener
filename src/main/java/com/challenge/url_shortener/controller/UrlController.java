package com.challenge.url_shortener.controller;

import com.challenge.url_shortener.model.Url;
import com.challenge.url_shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {
    @Autowired
    private UrlService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Url createUrl(@RequestBody Url url){
        return service.addUrl(url);
    }
}
