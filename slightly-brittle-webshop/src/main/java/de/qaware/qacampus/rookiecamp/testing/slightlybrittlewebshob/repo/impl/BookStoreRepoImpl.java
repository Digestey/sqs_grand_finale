package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.repo.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.OverallStorage;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.repo.BookStoreRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookStoreRepoImpl implements BookStoreRepo {
    private final String DEFAULT_STORE = "DEFAULT_STORE";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private JpaBookStoreRepo repo;

    @Autowired
    public BookStoreRepoImpl(JpaBookStoreRepo repo) {
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.repo = repo;
    }

    @Override
    public OverallStorage getStorage() {
        if (repo.existsById(DEFAULT_STORE))
            return mapFromDao(repo.getById(DEFAULT_STORE));
        return new OverallStorage();
    }

    @Override
    public void setStorage(OverallStorage storage) {
        OverallStorageDAO dao = mapToDao(storage);

        if (repo.existsById(dao.getId())){
            repo.updateRepo(dao.getId(), dao.getJsonDoc());
        } else {
            repo.insert(dao.getId(), dao.getJsonDoc());
        }
    }

    @SneakyThrows
    private OverallStorageDAO mapToDao(OverallStorage storage) {
        OverallStorageDAO dao = new OverallStorageDAO();

        dao.setId(DEFAULT_STORE);
        dao.setJsonDoc(objectMapper.writeValueAsString(storage));

        return dao;
    }

    @SneakyThrows
    private OverallStorage mapFromDao(OverallStorageDAO dao) {
        return objectMapper.readValue(dao.getJsonDoc(), OverallStorage.class);
    }


}
