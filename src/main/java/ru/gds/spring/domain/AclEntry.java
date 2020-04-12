package ru.gds.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "acl_entry")
public class AclEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "acl_object_identity", nullable = false)
    private long aclObjectIdentity;

    @Column(name = "ace_order", nullable = false)
    private int aceOrder;

    @Column(name = "sid", nullable = false)
    private long sid;

    @Column(name = "mask", nullable = false)
    private int mask;

    @Column(name = "granting", nullable = false)
    private int granting;

    @Column(name = "audit_success", nullable = false)
    private int auditSuccess;

    @Column(name = "audit_failure", nullable = false)
    private int auditFailure;

    public AclEntry(){}

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