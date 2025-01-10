package com.opcr.poseidon.validation;

import com.opcr.poseidon.controllers.RuleNameController;
import com.opcr.poseidon.domain.RuleName;
import com.opcr.poseidon.services.RuleNameService;
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

@WebMvcTest(RuleNameController.class)
public class RuleNameValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameService ruleNameService;

    @Test
    public void validate() throws Exception {
        doNothing().when(ruleNameService).saveRuleName(any(RuleName.class));
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(redirectedUrl("/ruleName/list"));
        verify(ruleNameService).saveRuleName(any(RuleName.class));
    }

    @Test
    public void whenNameIsEmptyShowError() throws Exception {
        doNothing().when(ruleNameService).saveRuleName(any(RuleName.class));
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(content().string(containsString("Name is mandatory.")));
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }

    @Test
    public void whenDescriptionIsEmptyShowError() throws Exception {
        doNothing().when(ruleNameService).saveRuleName(any(RuleName.class));
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", "name")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(content().string(containsString("Description is mandatory.")));
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }

    @Test
    public void whenJsonIsEmptyShowError() throws Exception {
        doNothing().when(ruleNameService).saveRuleName(any(RuleName.class));
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", "name")
                        .param("description", "description")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(content().string(containsString("Json is mandatory.")));
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }

    @Test
    public void whenTemplateIsEmptyShowError() throws Exception {
        doNothing().when(ruleNameService).saveRuleName(any(RuleName.class));
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(content().string(containsString("Template is mandatory.")));
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }

    @Test
    public void whenSqlStrIsEmptyShowError() throws Exception {
        doNothing().when(ruleNameService).saveRuleName(any(RuleName.class));
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlPart", "sqlPart"))
                .andExpect(content().string(containsString("SQL is mandatory.")));
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }

    @Test
    public void whenSqlPartIsEmptyShowError() throws Exception {
        doNothing().when(ruleNameService).saveRuleName(any(RuleName.class));
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr"))
                .andExpect(content().string(containsString("SQL Part is mandatory.")));
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }
}
