package com.company.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FileDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Type(type="org.hibernate.type.BinaryType")
    private byte[] file;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
