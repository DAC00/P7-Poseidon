package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.User;
import com.opcr.poseidon.services.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private User userTest;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        this.userTest = new User();
        userTest.setUsername("newAdmin");
        userTest.setFullname("FullName");
        userTest.setPassword("@1Test1@");
        userTest.setRole("ADMIN");
    }

    @Test
    public void saveUserTest() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("username", userTest.getUsername())
                        .param("password", userTest.getPassword())
                        .param("fullname", userTest.getFullname())
                        .param("role", userTest.getRole()))
                .andExpect(redirectedUrl("/user/list"));

        List<User> list = userService.getUsers();
        assertEquals(3, list.size());
        assertTrue(list.stream().anyMatch(
                uList -> uList.getUsername().equals(userTest.getUsername())
                        && uList.getFullname().equals(userTest.getFullname())
                        && uList.getRole().equals(userTest.getRole())
                        && passwordEncoder.matches(userTest.getPassword(), uList.getPassword())
        ));
    }

    @Test
    public void updateUserTest() throws Exception {
        userService.saveUser(userTest);
        String updatedPassword = "@2UPDate2@";

        mockMvc.perform(post("/user/update/" + userTest.getId())
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("username", userTest.getUsername())
                        .param("password", updatedPassword)
                        .param("fullname", userTest.getFullname())
                        .param("role", userTest.getRole()))
                .andExpect(redirectedUrl("/user/list"));

        List<User> list = userService.getUsers();
        assertEquals(3, list.size());
        assertTrue(list.stream().anyMatch(
                uList -> uList.getUsername().equals(userTest.getUsername())
                        && uList.getFullname().equals(userTest.getFullname())
                        && uList.getRole().equals(userTest.getRole())
                        && passwordEncoder.matches(updatedPassword, uList.getPassword())
        ));
    }

    @Test
    public void deleteUserTest() throws Exception {
        userService.saveUser(userTest);

        mockMvc.perform(get("/user/delete/" + userTest.getId())
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(redirectedUrl("/user/list"));

        assertEquals(2, userService.getUsers().size());
    }
}
