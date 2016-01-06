package com.lse.spring.example.data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "audit_id")
    private String id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    @Column(name="audit_msg")
    private String message;

    @Column(name="audit_created_ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTs;

    public Audit() {
    }

    public Audit(String message) {
        this.message=message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Date createdTs) {
        this.createdTs = createdTs;
    }

    @PrePersist
    public void prePersist() {
        createdTs = new Date();
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", createdTs=" + createdTs +
                '}';
    }
}