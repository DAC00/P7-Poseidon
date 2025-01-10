package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.User;
import com.opcr.poseidon.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(UserController.class)
public class UserValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void validate() throws Exception {
        doNothing().when(userService).saveUser(any(User.class));
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("username", "username")
                        .param("password", "Aa1aAa@a")
                        .param("fullname", "fullname")
                        .param("role", "ADMIN"))
                .andExpect(redirectedUrl("/user/list"));
        verify(userService).saveUser(any(User.class));
    }

    @Test
    public void whenPasswordIsEmptyShowError() throws Exception {
        doNothing().when(userService).saveUser(any(User.class));
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("username", "username")
                        .param("fullname", "fullname")
                        .param("role", "ADMIN"))
                .andExpect(content().string(containsString("Password is mandatory.")));
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void whenPasswordIsNotValidShowError() throws Exception {
        doNothing().when(userService).saveUser(any(User.class));
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("username", "username")
                        .param("password", "password")
                        .param("fullname", "fullname")
                        .param("role", "ADMIN"))
                .andExpect(content().string(containsString("Password needs : 8 characters,")));
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void whenUsernameIsEmptyShowError() throws Exception{
        doNothing().when(userService).saveUser(any(User.class));
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("password", "Aa1aAa@a")
                        .param("fullname", "fullname")
                        .param("role", "ADMIN"))
                .andExpect(content().string(containsString("Username is mandatory.")));
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void whenFullNameIsEmptyShowError() throws Exception{
        doNothing().when(userService).saveUser(any(User.class));
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("username", "username")
                        .param("password", "Aa1aAa@a")
                        .param("role", "ADMIN"))
                .andExpect(content().string(containsString("FullName is mandatory.")));
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void whenRoleIsEmptyShowError() throws Exception{
        doNothing().when(userService).saveUser(any(User.class));
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .param("username", "username")
                        .param("password", "Aa1aAa@a")
                        .param("fullname", "fullname"))
                .andExpect(content().string(containsString("Role is mandatory.")));
        verify(userService, never()).saveUser(any(User.class));
    }
}
