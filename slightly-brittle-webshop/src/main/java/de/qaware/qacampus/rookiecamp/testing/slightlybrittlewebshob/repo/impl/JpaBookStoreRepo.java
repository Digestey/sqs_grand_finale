package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.repo.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface JpaBookStoreRepo extends JpaRepository<OverallStorageDAO, String> {

    @Query(value = "INSERT INTO STORAGE(ID, JSON_DOC) VALUES (?1, ?2)", nativeQuery = true)
    @Modifying
    void insert(String storage, String jsonDoc);

    @Query(value = "UPDATE STORAGE SET JSON_DOC = ?2 WHERE ID = ?1", nativeQuery = true)
    @Modifying
    void updateRepo(String storage, String jsonDoc);
}
