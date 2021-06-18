package com.tinyurl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="longurls2")
public class Longurl {

    @Id
    private String id;
    private final String lUrl;


    
    public Longurl(String lUrl) {
        this.lUrl = lUrl;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String getlUrl() {
        return lUrl;
    }


    @Override
    public String toString() {
        return "Longurl [id=" + id + ", lUrl=" + lUrl + "]";
    }

    

}
