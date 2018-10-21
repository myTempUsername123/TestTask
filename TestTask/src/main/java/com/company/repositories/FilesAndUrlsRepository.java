package com.company.repositories;

import com.company.models.FileDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


public class FilesAndUrlsRepository {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("DataStoringPersistenceUnit").createEntityManager();

    public String addFile(FileDao fileDao) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(fileDao);
        transaction.commit();
        return fileDao.getUrl();
    }


    public FileDao getFileById(long id) {
        return entityManager.find(FileDao.class, id);
    }

    public List<Object[]> getAllUrls(){
        String query = ("SELECT id, url FROM files");
        Query nativeQuery = entityManager.createNativeQuery(query);
        List<Object[]> resultList = nativeQuery.getResultList();
        return resultList;
    }
}
