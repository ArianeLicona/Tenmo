package com.techelevator.contoller;

import com.techelevator.tenmo.controller.UserController;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTests {
    @InjectMocks
    private UserController userController;
    @Mock
    private JdbcUserDao userDao;
    private final List<User> USERS = new ArrayList<>();
    private User testUser = new User();
    private MockMvc mockMvc;

    @Before
    public void setup() {

        USERS.add(new User(1,"test","test","name"));
        USERS.add(new User(2,"test2","test2","name"));

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        // Arrange
        when(userDao.findAll()).thenReturn(USERS);

        // Act and Assert
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("test")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("test2")));
    }
    @Test
    public void getUserByIdTest() throws Exception{
        testUser = new User(1,"test3","test3","name");
        when(userDao.getUserById(1)).thenReturn(testUser);
        mockMvc.perform(get("/users/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("test3")));
    }
}

