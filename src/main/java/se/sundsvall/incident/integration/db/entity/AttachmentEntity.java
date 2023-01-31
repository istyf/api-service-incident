package se.sundsvall.incident.integration.db.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Attachments")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
public class AttachmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "IncidentId")
    private String incidentId;
    @Column(name = "category")
    private String category;
    @Column(name = "extension")
    private String extension;
    @Column(name = "mimetype")
    private String mimeType;
    @Column(name = "note")
    private String note;
    @Column(name = "file")
    private String file;
    @Column(name = "name")
    private String name;
    @Column(name = "Created")
    private String created;
}