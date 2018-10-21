package com.company.services;

import com.company.exceptions.NotFoundException;
import com.company.models.FileDao;
import com.company.repositories.FilesAndUrlsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileServiceTest {

    @Mock
    private FilesAndUrlsRepository repository;
    private FileService fileService;
    private String validUrl;
    private long validId;
    private long invalidId;
    private FileDao fileDao;

    @Before
    public void prepare(){
        MockitoAnnotations.initMocks(this);
        fileService = new FileService(repository);
        validUrl = "https://www.google.com/";
        validId = 1;
        invalidId = -1;
        fileDao = new FileDao();
        fileDao.setUrl(validUrl);
        fileDao.setId(validId);
        fileDao.setFile(new byte[0]);
        prepareMocks();
    }

    private void prepareMocks() {
        Mockito.when(repository.addFile(Mockito.any())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return validUrl;
            }
        });

        Mockito.when(repository.getAllUrls()).thenAnswer(new Answer<List<String>>() {
            @Override
            public List<String> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<String> stringList = new LinkedList<>();
                stringList.add(validUrl);
                return stringList;
            }
        });
        Mockito.when(repository.getFileById(validId)).thenAnswer(new Answer<FileDao>() {
            @Override
            public FileDao answer(InvocationOnMock invocationOnMock) throws Throwable {
                return fileDao;
            }
        });
        Mockito.when(repository.getFileById(invalidId)).thenReturn(null);
    }

    @Test
    public void getFileById_gettingFileWithValidId_fileDtoReturned(){
        //when
        File fileById = fileService.getFileById(validId);
        //then
        Assert.assertNotNull(fileById);
    }

    @Test(expected = NotFoundException.class)
    public void getFileById_gettingNotExistingFile_exceptionThrow(){
        //when
        fileService.getFileById(invalidId);
    }


}