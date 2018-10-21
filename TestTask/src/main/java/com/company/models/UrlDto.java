package com.company.models;


public class UrlDto {

    private long id;

    private String Url;

    public UrlDto() {
        id=0;
    }

    public UrlDto(long id, String url) {
        this.id = id;
        Url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UrlDto(String url) {
        Url = url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "Url='" + Url + '\'' +
                '}';
    }
}
