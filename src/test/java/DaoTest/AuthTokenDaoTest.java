package DaoTest;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {
    Database db;
    AuthToken authToken;
    AuthTokenDao aDao;



    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        authToken = new AuthToken("Tanner123A", "Tanner123A");
        Connection conn = db.getConnection();
        aDao = new AuthTokenDao(conn);
        aDao.clear();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    void insertPass() {
        try {
            aDao.insert(authToken);
            AuthToken compareTest = aDao.find(authToken.getAuthToken());
            assertNotNull(compareTest);
            assertEquals(authToken.getAuthToken(), compareTest.getAuthToken());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void insertSecond() throws DataAccessException {
        AuthToken authToken1 = new AuthToken("Tanner123bb", "Tanner123b");
        aDao.insert(authToken);
        aDao.insert(authToken1);
        AuthToken compareTest = aDao.find(authToken.getAuthToken());
        assertNull(compareTest);
    }

    @Test
    void findPass() throws DataAccessException {
        try {
            aDao.insert(authToken);
            AuthToken compareTest = aDao.find(authToken.getAuthToken());
            assertNotNull(compareTest);
            assertEquals(authToken.getAuthToken(), compareTest.getAuthToken());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void findFail() throws DataAccessException {
        try{
            aDao.find(null);
            fail("Should have thrown an exception");
        }catch (DataAccessException e){
            assertNotNull(e);
        }

    }

    @Test
    void clear() throws DataAccessException {
        aDao.insert(authToken);
        aDao.clear();
        AuthToken compareTest = aDao.find(authToken.getAuthToken());
        assertNull(compareTest);
    }

}