package demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.domain.User;
import demo.serviceLayer.Service;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Service service;

    private JacksonTester<User> jsonTenant;
    private JacksonTester<List<User>> jsonTenantList;

    public User user1;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        user1 = new User("1", "name@tenantspace.com", "312-555-5555", "Super Admin", "123 Main St", "Portland", "OR", "80123", "US", "ComEd");

        User user2 = new User("2","name@ge.com", "847-555-5555", "Admin", "4455 North Monroe", "Paris", "TN", "74364", "US", "General Electric");

        user1.setUserid("1");
        user2.setUserid("2");

        List<User> comEdUserList = new ArrayList<User>() {{
            add(user1);
        }};

        List<User> generalElectricUserList = new ArrayList<User>() {{
            add(user2);
        }};

        given(service.findByUserId("1")).willReturn(user1);
        given(service.findByUserId("2")).willReturn(user2);
        given(service.findAllUsersByUserType("Admin", "ComEd")).willReturn(comEdUserList);
        given(service.findAllUsersByUserType("Admin", "GE")).willReturn(generalElectricUserList);


        List<User> comEdReturnValue = service.findAllUsersByUserType("Admin", "ComEd");
        List<User> generalElectricReturnValue = service.findAllUsersByUserType("Admin", "ComEd");


        assertEquals(comEdUserList, comEdReturnValue);
        assertEquals(generalElectricUserList, generalElectricReturnValue);


    }

    @Test
    public void shouldGetUserByUserId() throws Exception {
        assertEquals(service.findByUserId("1"), user1);
    }

//    @Test(expected = UnprocessableEntity.class)
//    public void shouldRespondWithException_IfProvidedWithIncorrectIdOrIdDoesNotExistInDatabase_WhenGetUserByUserIdIsCalled() throws Exception {
//        Exception exception = assertThrows(HttpClientErrorException.UnprocessableEntity.class, service.findByUserId("1d"));
//    }


    @Test
    public void shouldGetUserByUserType() throws Exception {
        user1 = new User("1", "name@tenantspace.com", "312-555-5555", "Super Admin", "123 Main St", "Portland", "OR", "80123", "US", "GE");
        user1.setId(1l);
        List users = new ArrayList();

        given(service.findAllUsersByUserType("Super Admin", "GE")).willReturn(users);

        List<User> expectedUsers = service.findAllUsersByUserType("Super Admin", "GE");

        assertEquals(expectedUsers, users);
    }

    @Test
    public void shouldNotGetUserByUserType_WhenTenantOrUserTypeDoesNotExistInDB() throws Exception {
        // The User Model needs a tenant option
        // The User Management Services may need a feign client for the Tenant Management Service
        // Tenant tenant = new Tenant();
        // User user = new User();
        // given().willReturn()


        when(service.findAllUsersByUserType("Super Admin", "ComEd")).thenThrow(HttpClientErrorException.UnprocessableEntity.class);

        Exception exception = assertThrows(HttpClientErrorException.UnprocessableEntity.class, () -> {
            service.findAllUsersByUserType("Super Admin", "ComEd");
        });

        String expectedMessage = "Unprocessable Entity Exception";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
