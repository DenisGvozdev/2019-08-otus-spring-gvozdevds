package ru.gds.spring.microservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "acl_class")
public class AclClass {

    @Id
    private long id;
    private String classs;

    public AclClass(){}

    public AclClass(long id, String classs){
        this.id = id;
        this.classs = classs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }
}