package ru.gds.spring.microservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "acl_object_identity")
public class AclObjectIdentity {

    @Id
    private long id;
    private long objectIdClass;
    private long objectIdIdentity;
    private Long parentObject;
    private long ownerSid;
    private long entriesInheriting;

    public AclObjectIdentity() {
    }

    public AclObjectIdentity(
            long id,
            long objectIdClass,
            long objectIdIdentity,
            Long parentObject,
            long ownerSid,
            long entriesInheriting) {
        this.id = id;
        this.objectIdClass = objectIdClass;
        this.objectIdIdentity = objectIdIdentity;
        this.parentObject = parentObject;
        this.ownerSid = ownerSid;
        this.entriesInheriting = entriesInheriting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getObjectIdClass() {
        return objectIdClass;
    }

    public void setObjectIdClass(long objectIdClass) {
        this.objectIdClass = objectIdClass;
    }

    public long getObjectIdIdentity() {
        return objectIdIdentity;
    }

    public void setObjectIdIdentity(long objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }

    public Long getParentObject() {
        return parentObject;
    }

    public void setParentObject(Long parentObject) {
        this.parentObject = parentObject;
    }

    public long getOwnerSid() {
        return ownerSid;
    }

    public void setOwnerSid(long ownerSid) {
        this.ownerSid = ownerSid;
    }

    public long getEntriesInheriting() {
        return entriesInheriting;
    }

    public void setEntriesInheriting(long entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }
}

