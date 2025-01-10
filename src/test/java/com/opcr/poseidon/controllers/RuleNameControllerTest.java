package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.RuleName;
import com.opcr.poseidon.services.RuleNameService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameService ruleNameService;

    private RuleName ruleNameTest;

    @BeforeEach
    public void setUp() {
        this.ruleNameTest = new RuleName();
        ruleNameTest.setName("Name");
        ruleNameTest.setDescription("Description");
        ruleNameTest.setJson("Json");
        ruleNameTest.setTemplate("Template");
        ruleNameTest.setSqlStr("SQL");
        ruleNameTest.setSqlPart("SQL Part");
    }

    @Test
    public void saveRuleNameTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", ruleNameTest.getName())
                        .param("description", ruleNameTest.getDescription())
                        .param("json", ruleNameTest.getJson())
                        .param("template", ruleNameTest.getTemplate())
                        .param("sqlStr", ruleNameTest.getSqlStr())
                        .param("sqlPart", ruleNameTest.getSqlPart()))
                .andExpect(redirectedUrl("/ruleName/list"));

        List<RuleName> list = ruleNameService.getRuleNames();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                rList -> rList.getName().equals(ruleNameTest.getName())
                        && rList.getDescription().equals(ruleNameTest.getDescription())
                        && rList.getJson().equals(ruleNameTest.getJson())
                        && rList.getTemplate().equals(ruleNameTest.getTemplate())
                        && rList.getSqlStr().equals(ruleNameTest.getSqlStr())
                        && rList.getSqlPart().equals(ruleNameTest.getSqlPart())
        ));
    }

    @Test
    public void updateRuleNameTest() throws Exception {
        ruleNameService.saveRuleName(ruleNameTest);
        String updatedDescription = "TEST";

        mockMvc.perform(post("/ruleName/update/" + ruleNameTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("name", ruleNameTest.getName())
                        .param("description", updatedDescription)
                        .param("json", ruleNameTest.getJson())
                        .param("template", ruleNameTest.getTemplate())
                        .param("sqlStr", ruleNameTest.getSqlStr())
                        .param("sqlPart", ruleNameTest.getSqlPart()))
                .andExpect(redirectedUrl("/ruleName/list"));

        List<RuleName> list = ruleNameService.getRuleNames();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                rList -> rList.getName().equals(ruleNameTest.getName())
                        && rList.getDescription().equals(updatedDescription)
                        && rList.getJson().equals(ruleNameTest.getJson())
                        && rList.getTemplate().equals(ruleNameTest.getTemplate())
                        && rList.getSqlStr().equals(ruleNameTest.getSqlStr())
                        && rList.getSqlPart().equals(ruleNameTest.getSqlPart())
        ));
    }

    @Test
    public void deleteRuleNameTest() throws Exception {
        ruleNameService.saveRuleName(ruleNameTest);

        mockMvc.perform(get("/ruleName/delete/" + ruleNameTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER")))
                .andExpect(redirectedUrl("/ruleName/list"));

        assertEquals(0, ruleNameService.getRuleNames().size());
    }
}
