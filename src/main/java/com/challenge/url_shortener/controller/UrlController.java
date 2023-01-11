package com.challenge.url_shortener.controller;

import com.challenge.url_shortener.model.Url;
import com.challenge.url_shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/url")
public class UrlController {
    @Autowired
    public UrlService service;

    @GetMapping()
    @ResponseStatus()
    public String hello(){
        return "Olá";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUrl(@RequestBody Url url){
        // No create -> verificação caso já exista uma Url origin
        Url createdUrl = service.addUrl(url);

        return "Url criada com sucesso, link de acesso : http://localhost:8080/url/short/"+createdUrl.getUrlAccess();
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Url> listAllUrls(){
        return service.findAllUrls();
    }

    @GetMapping("/short/{codeAccess}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> redirectToOrigin (@PathVariable String codeAccess) throws URISyntaxException {

        Url url = service.getByUrlAccess(codeAccess);

        URI uri = new URI("http://" + url.getUrlOrigin());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public String listByNumAccess(){
        List<Url> urls = service.getUrlsByNumAccess();

        int numOfAccess = 0;
        String returnStatistics = "";

        for(int i = 0; i < urls.size(); i++){
            numOfAccess += urls.get(i).getNumAccess();
        }

        returnStatistics += "Total de acessos à API : " + numOfAccess + "\n----------------\n";

        for(int i = 0; i < urls.size(); i++){
            returnStatistics += "Url: "+ urls.get(i).getUrlAccess() +"\nNúmero de acessos : "+ urls.get(i).getNumAccess() +"\n% de acessos : "+ (urls.get(i).getNumAccess() * 100) / numOfAccess + "% \n\n";
        }

        return returnStatistics;
    }

    @GetMapping("/get/{urlId}")
    @ResponseStatus(HttpStatus.OK)
    public Url findById(@PathVariable String urlId){
        return service.getUrlById(urlId);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Url updateUrl(@RequestBody Url url){
        return service.updateUrl(url);
    }

    @DeleteMapping("/delete/{urlId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUrl(@PathVariable String urlId){
        return service.deleteUrl(urlId);
    }
}
