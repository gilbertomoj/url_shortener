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
    @ResponseStatus(HttpStatus.OK)
    public String hello(){
        String initialMessage = "" +
                "### Acesso à url com link encurtado\n" +
                "GET http://localhost:8080/url/short/{urlCodeAccess}\n\n" +
                "### Criação de Urls\n" +
                "POST http://localhost:8080/url/create\n\n" +
                "### Listagem de todas as Urls\n" +
                "GET http://localhost:8080/url/list\n\n" +
                "### Listagem das Urls por numero de acesso\n" +
                "GET http://localhost:8080/url/listBy/accessNumber\n\n" +
                "### Acesso às estatisticas da API\n" +
                "GET http://localhost:8080/url/statistics\n\n" +
                "### Acesso aos detalhes de uma Url\n" +
                "GET http://localhost:8080/url/get/{{urlId}}\n\n"+
                "### Atualização de uma Url\n" +
                "PUT http://localhost:8080/url/update\n\n" +
                "### Delete de uma Url\n" +
                "DELETE http://localhost:8080/url/delete/{{urlId}}" +
                "";

        return initialMessage;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUrl(@RequestBody Url url){
        // No create -> verificação caso já exista uma Url origin
        Url createdUrl = service.addUrl(url);

        return "Url criada com sucesso, link de acesso : localhost:8080/url/short/"+createdUrl.getUrlAccess();
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
            returnStatistics += "Url: http://localhost:8080/url/short/"+ urls.get(i).getUrlAccess() +"\nNúmero de acessos : "+ urls.get(i).getNumAccess() +"\n% de acessos : "+ (urls.get(i).getNumAccess() * 100) / numOfAccess + "% \n\n";
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
