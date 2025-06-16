package com.flixfix.sqs_testing.test_containers;

import com.flixfix.sqs_testing.persistence.BookStoragePort;
import com.flixfix.sqs_testing.service.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Testcontainers
public class TestContainersLibraryTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0-oracle")
            .withDatabaseName("library")
            .withUsername("user")
            .withPassword("password");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }

    @Autowired
    private BookStoragePort bookStoragePort;

    @Test
    void shouldSaveAndFindByLentFalse() {
        var book = new BookDto(null, "MySQL Book", false);
        bookStoragePort.save(book);

        List<BookDto> available = bookStoragePort.findByLentFalse();

        assertFalse(available.isEmpty());
    }
}
