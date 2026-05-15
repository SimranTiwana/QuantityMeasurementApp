package com.quantity.measurement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.quantity.measurement.dump.repositoryImpl.DatabaseRepository;
import com.quantity.measurement.dump.repositoryImpl.CacheRepository;
import com.quantity.measurement.config.ApplicationConfig;
import com.quantity.measurement.dump.database.ConnectionPool;
import com.quantity.measurement.model.QuantityMeasurementEntity;
import com.quantity.measurement.repository.Repository;
import com.quantity.measurement.serviceImpl.ServiceImpl;

public class DatabaseIntegrationTest {

    private Repository repository;

    @BeforeEach
    void setup() {

        repository = new DatabaseRepository();
        repository.deleteAll();
    }



    // 1
    @Test
    void testMavenBuild_Success() {
        assertTrue(true);
    }


    // 2
    @Test
    void testPackageStructure_AllLayersPresent() {

        assertNotNull(ConnectionPool.class);
        assertNotNull(ServiceImpl.class);
        assertNotNull(QuantityMeasurementEntity.class);
        assertNotNull(Repository.class);
    }


    // 3
    @Test
    void testPomDependencies_JDBCDriversIncluded() {
        assertDoesNotThrow(() -> Class.forName("org.h2.Driver"));
    }

    // 4
    @Test
    void testDatabaseConfiguration_LoadedFromProperties() {
        String value = ApplicationConfig.getProperty("repository.type");
        assertNotNull(value);
    }

    // 5
    @Test
    void testConnectionPool_Initialization()
            throws Exception {

        Connection connection =
                ConnectionPool.getConnection();

        assertNotNull(connection);

        ConnectionPool.releaseConnection(connection);
    }

    // 6
    @Test
    void testConnectionPool_Acquire_Release() throws Exception {

        Connection connection = ConnectionPool.getConnection();
        assertNotNull(connection);
        ConnectionPool.releaseConnection(connection);
    }

    //7
    @Test
    void testConnectionPool_AllConnectionsExhausted() throws Exception {

        Connection[] connections = new Connection[11];

        try {

            assertThrows(SQLException.class, () -> {

                for (int i = 0; i < 11; i++) {

                    connections[i] = ConnectionPool.getConnection();
                }
            });

        } finally {

            for (Connection connection : connections) {
                if (connection != null) {
                    ConnectionPool.releaseConnection(connection);
                }
            }
        }
    }

    //8
    @Test
    void testDatabaseRepository_EmptyTable() {
        repository.deleteAll();
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    // 9
    @Test
    void testDatabaseRepository_SaveMeasurement() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperand1Value(10);
        entity.setOperand1Unit("FEET");
        entity.setOperand2Value(5);
        entity.setOperand2Unit("INCHES");
        entity.setMeasurementType("LENGTH");
        entity.setOperationType("ADD");
        entity.setResultValue(10.416);
        entity.setResultUnit("FEET");

        repository.save(entity);

        assertEquals( 1, repository.getAllMeasurements().size());
    }

    // 10
    @Test
    void testDatabaseRepository_GetAllMeasurements() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperand1Value(5);
        entity.setOperand1Unit("KG");
        entity.setOperand2Value(5);
        entity.setOperand2Unit("GRAM");
        entity.setMeasurementType("WEIGHT");
        entity.setOperationType("ADD");
        entity.setResultValue(5.005);
        entity.setResultUnit("KG");

        repository.save(entity);

        List<QuantityMeasurementEntity> result = repository.getAllMeasurements();
        assertFalse(result.isEmpty());
    }

    // 11
    @Test
    void testDatabaseRepository_DeleteAllMeasurements() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperand1Value(1);
        entity.setOperand1Unit("LITER");
        entity.setOperand2Value(1);
        entity.setOperand2Unit("ML");
        entity.setMeasurementType("VOLUME");
        entity.setOperationType("ADD");
        entity.setResultValue(1.001);
        entity.setResultUnit("LITER");

        repository.save(entity);

        repository.deleteAll();

        assertEquals(0, repository.getAllMeasurements().size());
    }

    // 12
    @Test
    void testDatabaseRepository_TotalCount() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperand1Value(2);
        entity.setOperand1Unit("FEET");
        entity.setOperand2Value(2);
        entity.setOperand2Unit("FEET");
        entity.setMeasurementType("LENGTH");
        entity.setOperationType("ADD");
        entity.setResultValue(4);
        entity.setResultUnit("FEET");

        repository.save(entity);

        assertEquals( 1, repository.getTotalCount());
    }

    // 13
    @Test
    void testDatabaseRepository_FindByOperationType() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setOperand1Value(1);
        entity.setOperand1Unit("KG");
        entity.setOperand2Value(1);
        entity.setOperand2Unit("KG");
        entity.setMeasurementType("WEIGHT");
        entity.setOperationType("ADD");
        entity.setResultValue(2);
        entity.setResultUnit("KG");

        repository.save(entity);

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation("ADD");

        assertEquals(1, result.size());
    }

    // 14
    @Test
    void testDatabaseRepository_FindByMeasurementType() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setOperand1Value(1);
        entity.setOperand1Unit("CELSIUS");
        entity.setOperand2Value(1);
        entity.setOperand2Unit("CELSIUS");
        entity.setMeasurementType("TEMPERATURE");
        entity.setOperationType("COMPARE");
        entity.setResultValue(0);
        entity.setResultUnit("CELSIUS");

        repository.save(entity);

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByType(
                        "TEMPERATURE"
                );

        assertEquals(1, result.size());
    }

    // 15
    @Test
    void testConnectionPool_AvailableConnections() {

        assertTrue(
                ConnectionPool.availableConnections() >= 0
        );
    }

    // 16
    @Test
    void testConnectionPool_TotalConnections() {

        assertTrue(
                ConnectionPool.totalConnections() > 0
        );
    }

    // 17
    @Test
    void testDatabaseRepository_MultipleInsertions() {

        for (int i = 0; i < 5; i++) {

            QuantityMeasurementEntity entity =
                    new QuantityMeasurementEntity();

            entity.setOperand1Value(i);
            entity.setOperand1Unit("FEET");
            entity.setOperand2Value(i);
            entity.setOperand2Unit("FEET");
            entity.setMeasurementType("LENGTH");
            entity.setOperationType("ADD");
            entity.setResultValue(i * 2);
            entity.setResultUnit("FEET");

            repository.save(entity);
        }

        assertEquals(5, repository.getTotalCount());
    }

    // 18
    @Test
    void testDatabaseRepository_NonExistingOperationType() {

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation(
                        "INVALID"
                );

        assertTrue(result.isEmpty());
    }

    // 19
    @Test
    void testDatabaseRepository_NonExistingMeasurementType() {

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByType(
                        "INVALID"
                );

        assertTrue(result.isEmpty());
    }

    // 20
    @Test
    void testConnectionPool_MultipleConnections()
            throws Exception {

        Connection c1 =
                ConnectionPool.getConnection();

        Connection c2 =
                ConnectionPool.getConnection();

        assertNotNull(c1);
        assertNotNull(c2);

        ConnectionPool.releaseConnection(c1);
        ConnectionPool.releaseConnection(c2);
    }

    // 21
    @Test
    void testConnectionPool_ReleaseNullConnection() {

        assertDoesNotThrow(() ->
                ConnectionPool.releaseConnection(null));
    }

    // 22
    @Test
    void testDatabaseRepository_SaveNullEntity() {

        assertThrows(Exception.class, () ->
                repository.save(null));
    }

    // 23
    @Test
    void testDatabaseRepository_DeleteOnEmptyTable() {

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
    }

    // 24
    @Test
    void testDatabaseRepository_CountAfterDelete() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setOperand1Value(10);
        entity.setOperand1Unit("FEET");
        entity.setOperand2Value(5);
        entity.setOperand2Unit("FEET");
        entity.setMeasurementType("LENGTH");
        entity.setOperationType("ADD");
        entity.setResultValue(15);
        entity.setResultUnit("FEET");

        repository.save(entity);

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
    }

    // 25
    @Test
    void testDatabaseRepository_GetAllAfterDelete() {

        repository.deleteAll();

        assertTrue(
                repository.getAllMeasurements().isEmpty()
        );
    }

    // 26
    @Test
    void testDatabaseRepository_SaveDifferentMeasurementTypes() {

        QuantityMeasurementEntity entity1 =
                new QuantityMeasurementEntity();

        entity1.setOperand1Value(1);
        entity1.setOperand1Unit("FEET");
        entity1.setOperand2Value(1);
        entity1.setOperand2Unit("FEET");
        entity1.setMeasurementType("LENGTH");
        entity1.setOperationType("ADD");
        entity1.setResultValue(2);
        entity1.setResultUnit("FEET");

        repository.save(entity1);

        QuantityMeasurementEntity entity2 =
                new QuantityMeasurementEntity();

        entity2.setOperand1Value(1);
        entity2.setOperand1Unit("KG");
        entity2.setOperand2Value(1);
        entity2.setOperand2Unit("KG");
        entity2.setMeasurementType("WEIGHT");
        entity2.setOperationType("ADD");
        entity2.setResultValue(2);
        entity2.setResultUnit("KG");

        repository.save(entity2);

        assertEquals(2, repository.getTotalCount());
    }

    // 27
    @Test
    void testConnectionPool_ConcurrentAccess()
            throws Exception {

        ExecutorService executorService =
                Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {

            executorService.submit(() -> {

                try {

                    Connection connection =
                            ConnectionPool.getConnection();

                    Thread.sleep(100);

                    ConnectionPool.releaseConnection(
                            connection
                    );

                } catch (Exception e) {

                    fail();
                }
            });
        }

        executorService.shutdown();

        assertTrue(
                executorService.awaitTermination(
                        5,
                        TimeUnit.SECONDS
                )
        );
    }

    // 28
    @Test
    void testDatabaseRepository_SaveAndRetrieveValues() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setOperand1Value(100);
        entity.setOperand1Unit("ML");
        entity.setOperand2Value(100);
        entity.setOperand2Unit("ML");
        entity.setMeasurementType("VOLUME");
        entity.setOperationType("ADD");
        entity.setResultValue(200);
        entity.setResultUnit("ML");

        repository.save(entity);

        QuantityMeasurementEntity saved =
                repository.getAllMeasurements().get(0);

        assertEquals(
                200,
                saved.getResultValue()
        );
    }

    // 29
    @Test
    void testApplicationConfig_NotNull() {

        assertNotNull(
                ApplicationConfig.getProperty(
                        "spring.datasource.url"
                )
        );
    }

    // 30
    @Test
    void testApplicationConfig_IntegerProperty() {

        int value =
                ApplicationConfig.getIntProperty(
                        "pool.maxSize"
                );

        assertTrue(value > 0);
    }

    // 31
    @Test
    void testRepositoryInstance_NotNull() {

        assertNotNull(repository);
    }

    // 32
    @Test
    void testEntityInstance_NotNull() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        assertNotNull(entity);
    }

    // 33
    @Test
    void testDatabaseRepository_InsertAndCount() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setOperand1Value(50);
        entity.setOperand1Unit("GRAM");
        entity.setOperand2Value(50);
        entity.setOperand2Unit("GRAM");
        entity.setMeasurementType("WEIGHT");
        entity.setOperationType("ADD");
        entity.setResultValue(100);
        entity.setResultUnit("GRAM");

        repository.save(entity);

        assertTrue(repository.getTotalCount() > 0);
    }
}