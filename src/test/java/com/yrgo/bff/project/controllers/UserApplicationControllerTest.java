package com.yrgo.bff.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrgo.bff.project.controllers.ApplicationUserController;
import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.GameServiceImplementation;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@WebMvcTest
public class UserApplicationControllerTest {

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private UserAccountServiceImplementation userAccountServiceImplementation;

    @MockBean
    private GameServiceImplementation gameServiceImplementation;

    @Mock
    private UserAccountServiceImplementation userAccountServiceImplementation;

    @InjectMocks
    private ApplicationUserController ctrlr;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<ApplicationUser> jsonUser;
    private JacksonTester<Game> jsonGame;
    private ApplicationUser ereko;
    @BeforeEach
    void init() {

        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(ctrlr)
                .setControllerAdvice(new Exception())
                .build();

        ereko = new ApplicationUser("ereko", "password");
        Mockito.when(userAccountServiceImplementation.readUser(ereko.getUsername())).thenReturn(ereko);

    }


    /*@Test
    public void getMappingReadUser()  throws Exception {

        MockHttpServletResponse response = mvc.perform(getMappingReadUser("/user/name=ereko&password=password"));
    }*/
}
