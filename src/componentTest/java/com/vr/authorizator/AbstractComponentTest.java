package com.vr.authorizator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.authorizator.domain.repository.CardRepository;
import com.vr.authorizator.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

@ComponentTest
public abstract class AbstractComponentTest {

    @LocalServerPort
    protected String localPort;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected CardRepository cardRepository;

    @Autowired
    protected TransactionRepository transactionRepository;

    @Value("${local.url}")
    protected String localUrl ;

    @Container
    public static GenericContainer dbContainer = new MySQLContainer("mysql:5.7")
                .withDatabaseName("miniautorizador")
                .withEnv("MYSQL_ALLOW_EMPTY_PASSWORD","yes")
                .withEnv("MYSQL_ROOT_PASSWORD","");

}
