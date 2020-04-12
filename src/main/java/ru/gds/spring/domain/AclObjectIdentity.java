package ru.gds.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "acl_object_identity")
public class AclObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "object_id_class", nullable = false)
    private long objectIdClass;

    @Column(name = "object_id_identity", nullable = false)
    private long objectIdIdentity;

    @Column(name = "parent_object")
    private Long parentObject;

    @Column(name = "owner_sid")
    private long ownerSid;

    @Column(name = "entries_inheriting", nullable = false)
    private long entriesInheriting;

    public AclObjectIdentity(){}

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

