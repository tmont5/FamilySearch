package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private RegisterService rService;
    private RegisterRequest rRequest;

    private LoginService lService;
    private Database db;
    private LoginRequest lRequest;
    private LoginResult lResult;
    private UserDao uDao;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        rService = new RegisterService();
        rRequest = new RegisterRequest("username", "password", "email", "firstName", "lastName", "m", "personID");
        lService = new LoginService();
        lRequest = new LoginRequest("username", "password");
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
    void loginPass() {
        rService.register(rRequest);
        lResult = lService.login(lRequest);
        assertNotNull(lResult);
        assertNotNull(lResult.getAuthToken());
    }

    @Test
    void loginFail() {
        rService.register(rRequest);
        lRequest.setPassword("wrongPassword");
        lResult = lService.login(lRequest);
        assertEquals(lResult.getMessage(), "Error: Incorrect password");
    }
}