package com.company.services;

import com.company.exceptions.InvalidUrlException;
import com.company.models.UrlDto;
import com.company.repositories.FilesAndUrlsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class UrlServiceTest {

    @Mock
    private FilesAndUrlsRepository repository;
    @Mock
    private FileService fileService;
    private UrlService urlService;
    private String validUrl;
    private String invalidUrl;
    private List<UrlDto> allUrls;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        urlService = new UrlService(repository, fileService);
        validUrl = "https://www.google.com/";
        invalidUrl = "https://invalid";
        allUrls = new LinkedList<>();
        allUrls.add(new UrlDto(1, validUrl));
        prepareMocks();
    }

    private void prepareMocks() {
        List<Object[]> allUrls;
        allUrls = new LinkedList<>();
        BigInteger val = new BigInteger("1");
        allUrls.add(new Object[]{val, validUrl});
        Mockito.when(repository.getAllUrls()).thenReturn(allUrls);
        Mockito.when(fileService.addFile(Mockito.anyString())).thenReturn(true);
    }

    @Test
    public void addUrlAndStartDownloading_addingValidUrl_returnedUrlWithMessage(){
        //when
        String s = urlService.addUrlAndStartDownloading(validUrl);
        //then
        Assert.assertEquals(validUrl + " - Started downloading", s);
    }

    @Test(expected = InvalidUrlException.class)
    public void addUrlAndStartDownloading_addingInvalidUrl_returnedUrlWithMessageInvalid(){
        //when
        String s = urlService.addUrlAndStartDownloading(invalidUrl);
    }

    @Test
    public void getAllUrls_gettingAllAddedUrlFromDB_ListOfUrlDto(){
        //when
        List<UrlDto> allUrls = urlService.getAllUrls();
        //then
        Assert.assertEquals(this.allUrls.get(0).getUrl(), allUrls.get(0).getUrl());
        Assert.assertEquals(this.allUrls.get(0).getId(), allUrls.get(0).getId());
    }

    @Test
    public void addListOfUrlsAndStartDownloading_addingListOfValidUrls_callFileServiceAddFileEveryTime(){
        //given
        List<String> urlsToAdd = new LinkedList<>();
        urlsToAdd.add(validUrl);
        urlsToAdd.add("https://www.bing.com/");
        //when
        urlService.addListOfUrlsAndStartDownloading(urlsToAdd);
        //then
        Mockito.verify(fileService, Mockito.times(2)).addFile(Matchers.any());
    }




}
