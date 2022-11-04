package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.RegisterRequest;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    private Database db;
    private RegisterService rService;
    private RegisterRequest rRequest;
    private UserDao uDao;
    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        rService = new RegisterService();
        rRequest = new RegisterRequest("username", "password", "email", "firstName", "lastName", "m", "personID");
        db = new Database();
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        uDao.clear();
        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void registerPass() {
        RegisterResult rResult = rService.register(rRequest);
        assertNotNull(rResult);
        assertEquals(rRequest.getUserName(), rResult.getUserName());
    }

        @Test
    void registerFail() {
        RegisterResult rResult = rService.register(rRequest);
        assertNotNull(rResult);
        assertEquals(rRequest.getUserName(), rResult.getUserName());
        RegisterResult rResult2 = rService.register(rRequest);
        assertEquals(rResult2.getMessage(), "Error: Usernamem already exists");
    }
}