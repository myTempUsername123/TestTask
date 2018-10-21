package com.company.services;

import com.company.exceptions.NotFoundException;
import com.company.models.FileDao;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import com.company.repositories.FilesAndUrlsRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

public class FileService {
    private FilesAndUrlsRepository repository;
    private Queue<String> filesToDownload = new LinkedList<>();
    private boolean isDownloading = false;

    public FileService(FilesAndUrlsRepository repository) {
        this.repository = repository;
    }


    public boolean addFile(String fileUrl) {
        filesToDownload.add(fileUrl);
        if (!isDownloading) {
            isDownloading = true;
            new Thread(() ->
                    manageDownloadingQueue()
            ).start();
        }
        return true;
    }

    public File getFileById(long id) {
        FileDao fileById = repository.getFileById(id);
        if(fileById!=null) {
            File fileToReturn = new File(fileById.getUrl());
            try {
                FileUtils.writeByteArrayToFile(fileToReturn, fileById.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileToReturn;
        }else {
            throw new NotFoundException("file with id: " + id + "not found");
        }
    }

    private void manageDownloadingQueue() {
        while (!filesToDownload.isEmpty()) {
          downloadFile(filesToDownload.poll());
        }
        isDownloading = false;
    }

    private void downloadFile(String fileToDownload){
        InputStream inputStream = null;
        try {
            URL url = new URL(fileToDownload);
            inputStream = url.openStream();
            FileDao fileDao = new FileDao();
            fileDao.setFile(IOUtils.toByteArray(inputStream));
            fileDao.setUrl(url.toString());
            if (fileDao.getFile() != null) {
                repository.addFile(fileDao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(inputStream);
        }
    }

    private void closeStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
