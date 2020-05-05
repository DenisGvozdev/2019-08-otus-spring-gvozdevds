package ru.gds.spring.microservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "acl_entry")
public class AclEntry {

    @Id
    private long id;
    private long aclObjectIdentity;
    private int aceOrder;
    private long sid;
    private int mask;
    private int granting;
    private int auditSuccess;
    private int auditFailure;

    public AclEntry() {
    }

    public AclEntry(
            long id,
            long aclObjectIdentity,
            int aceOrder,
            long sid,
            int mask,
            int granting,
            int auditSuccess,
            int auditFailure) {
        this.id = id;
        this.aclObjectIdentity = aclObjectIdentity;
        this.aceOrder = aceOrder;
        this.sid = sid;
        this.mask = mask;
        this.granting = granting;
        this.auditSuccess = auditSuccess;
        this.auditFailure = auditFailure;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    public void setAclObjectIdentity(long aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    public int getAceOrder() {
        return aceOrder;
    }

    public void setAceOrder(int aceOrder) {
        this.aceOrder = aceOrder;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public int getGranting() {
        return granting;
    }

    public void setGranting(int granting) {
        this.granting = granting;
    }

    public int getAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(int auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    public int getAuditFailure() {
        return auditFailure;
    }

    public void setAuditFailure(int auditFailure) {
        this.auditFailure = auditFailure;
    }
}