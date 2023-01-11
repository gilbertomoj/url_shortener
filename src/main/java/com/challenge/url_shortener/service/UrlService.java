package com.challenge.url_shortener.service;

import com.challenge.url_shortener.model.Url;
import com.challenge.url_shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UrlService {
    @Autowired
    private UrlRepository repository;

    public Url addUrl(Url url){
        // Vai gerar o código de acesso da URL

//        // Verificar se já existe/foi criado um registro com a Url (A completar) !
//        Url foundUrl = repository.findByUrlOrigin(url.getUrlOrigin());
//        System.out.println(url.getUrlAccess());
//        System.out.println(foundUrl);

        // Gerar um método com retorno apenas do código !!
        String codeAccessGenerator = this.codeGenerator();

        url.setUrlId(UUID.randomUUID().toString().split("-")[0]);
        url.setUrlAccess(codeAccessGenerator);
        repository.save(url);
        return url;
    }

    public List<Url> findAllUrls(){
        return repository.findAll();
    }

    public Url getUrlById(String urlId){
        return repository.findById(urlId).get();
    }

    public Url getByUrlAccess(String urlAccess) {
        Date date = new Date();
        Url urlObj = repository.findByUrlAccess(urlAccess);

        urlObj.setLastAccess(date);
        urlObj.setNumAccess(urlObj.getNumAccess() + 1);
        repository.save(urlObj);
        return urlObj;
    }

    public List<Url> getUrlsByNumAccess(){
        List<Url> urls = repository.findAll(Sort.by(Sort.Direction.DESC, "numAccess"));
        return urls;
    }

    public Url updateUrl(Url urlRequest){
        Url existingUrl = repository.findById(urlRequest.getUrlId()).get();
        // Alterar apenas a Url de destino, manter sempre o código gerado (ex: www.google.com, www.yahoo.com)!
        existingUrl.setUrlOrigin(urlRequest.getUrlOrigin());
        return repository.save(existingUrl);
    }


    public String deleteUrl(String urlId){
        repository.deleteById(urlId);
        return "A url foi deletada com sucesso";
    }
    public Boolean findUrlByUrlOrigin(String urlOrigin){
        Url foundUrl = repository.findByUrlOrigin(urlOrigin);
        if(foundUrl == null){
            // Se não existir um registro com essa url, cria-se uma nova
            return Boolean.TRUE;
        }else{
            // Se existir retornar como false
            return Boolean.FALSE;
        }
    }
    public String codeGenerator(){
        String code = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVYWXZ1234567890";
        Random random = new Random();
        String codeAccessGenerator = "";
        int index = -1;
        for( int i = 0; i < 9; i++ ) {
            index = random.nextInt( code.length() );
            codeAccessGenerator += code.substring( index, index + 1 );
        }
        return codeAccessGenerator;
    }

}
