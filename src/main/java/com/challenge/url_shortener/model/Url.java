package com.challenge.url_shortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "url")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    @Id
    private String urlId;
    private String urlOrigin;
    private String urlAccess;
    private int numAccess;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastAccess;

}
