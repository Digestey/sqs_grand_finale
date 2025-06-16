package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.repo.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STORAGE")
public class OverallStorageDAO {
    @Id
    @Column(name = "ID", columnDefinition = "varchar")
    private String id;

    @Column(name = "JSON_DOC", columnDefinition = "text")
    private String jsonDoc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonDoc() {
        return jsonDoc;
    }

    public void setJsonDoc(String jsonDoc) {
        this.jsonDoc = jsonDoc;
    }
}
