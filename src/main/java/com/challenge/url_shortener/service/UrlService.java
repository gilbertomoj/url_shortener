package com.challenge.url_shortener.service;

import com.challenge.url_shortener.model.Url;
import com.challenge.url_shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UrlService {
    @Autowired
    private UrlRepository repository;

    public Url addUrl(Url url){
        url.setUrlId(UUID.randomUUID().toString().split("-")[0]);
        // Colocar o código de hash aqui !!!
        return repository.save(url);
    }

    public List<Url> findAllUrls(){
        return repository.findAll();
    }

    public Url getUrlById(String urlId){
        return repository.findById(urlId).get();
    }

    public Url getByAccessUrl(String accessUrl){
        return repository.findByAccessUrl(accessUrl);
    }

    public Url updateUrl(Url urlRequest){
        Url existingUrl = repository.findById(urlRequest.getUrlId()).get();
        // Alterar apenas a Url de destino, manter sempre o código gerado (ex: www.google.com, www.yahoo.com)!
        existingUrl.setUrlOrigin(urlRequest.getUrlOrigin());
        return repository.save(existingUrl);
    }

    public String updateLastAccess(String urlId, Date currentDate){
        Url existingUrl = repository.findById(urlId).get();
        existingUrl.setLastAccess(currentDate);
        repository.save(existingUrl);
        return "Ultimo acesso registrado";
    }

    public String deleteUrl(String urlId){
        repository.deleteById(urlId);
        return "A url foi deletada com sucesso";
    }


}
