package ru.gds.spring.microservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "acl_sid")
public class AclSid {

    @Id
    private long id;
    private int principal;
    private String sid;

    public AclSid(){}

    public AclSid(long id, int principal, String sid){
        this.id = id;
        this.principal = principal;
        this.sid = sid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrincipal() {
        return principal;
    }

    public void setPrincipal(int principal) {
        this.principal = principal;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}

