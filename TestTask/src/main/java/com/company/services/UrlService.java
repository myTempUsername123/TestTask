package com.company.services;

import com.company.exceptions.InvalidUrlException;
import com.company.exceptions.ServerException;
import com.company.models.UrlDto;
import com.company.repositories.FilesAndUrlsRepository;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class UrlService {

    private FileService fileService;
    private FilesAndUrlsRepository repository;

    public UrlService(FilesAndUrlsRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    public String addUrlAndStartDownloading(String urlToDownloadStr){
        UrlDto urlToDownload = new UrlDto(urlToDownloadStr);
        if(isUrlValid(urlToDownload)){
            if (fileService.addFile(urlToDownload.getUrl())){
                return urlToDownload.getUrl() + " - Started downloading";
            }else {
                throw new ServerException("internal server error");
            }
        }else {
            throw new InvalidUrlException(urlToDownload.getUrl() + " - is invalid");
        }
    }

    public List<String> addListOfUrlsAndStartDownloading(List<String> urlsToDownload){
        List<String> addedToDownload = new LinkedList<>();
        for (String urlToDownload: urlsToDownload) {
            addedToDownload.add(addUrlAndStartDownloading(urlToDownload));
        }
        return addedToDownload;
    }

    public List<UrlDto> getAllUrls() {
        List<Object[]> allObjects = repository.getAllUrls();
        List<UrlDto> allUrls = new LinkedList<>();
        for (Object[] object: allObjects) {
            UrlDto tempUrl = new UrlDto((String)object[1]);
            long tempId =((BigInteger)object[0]).longValue();
            tempUrl.setId(tempId);
            allUrls.add(tempUrl);
        }
        return allUrls;
    }

    private boolean isUrlValid(UrlDto url){
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url.getUrl()).openConnection();
            urlConnection.setRequestMethod("HEAD");
            if(urlConnection.getResponseCode() != 200){
                return false;
            }else {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }
}
