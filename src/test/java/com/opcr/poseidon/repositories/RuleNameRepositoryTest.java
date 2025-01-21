package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.RuleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class RuleNameRepositoryTest {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName;

    @BeforeEach
    public void setUp() {
        ruleName = new RuleName();
        ruleName.setName("name");
        ruleName.setDescription("desc");
        ruleName.setJson("json");
        ruleName.setTemplate("template");
        ruleName.setSqlPart("part");
        ruleName.setSqlStr("str");
    }

    @Test
    public void saveBidListTest() {
        ruleNameRepository.save(ruleName);
        assertTrue(ruleNameRepository.findAll().contains(ruleName));
        assertEquals(ruleNameRepository.findAll().size(), 1);
    }

    @Test
    public void updateBidListTest() {
        ruleNameRepository.save(ruleName);

        RuleName update = ruleNameRepository.findById(ruleName.getId()).orElseThrow();
        update.setDescription("TEST");
        ruleNameRepository.save(update);

        RuleName toVerify = ruleNameRepository.findById(update.getId()).orElseThrow();
        assertEquals(ruleNameRepository.findAll().size(), 1);
        assertEquals(toVerify.getDescription(), "TEST");
    }

    @Test
    public void deleteBidListTest() {
        ruleNameRepository.save(ruleName);
        assertEquals(ruleNameRepository.findAll().size(), 1);
        ruleNameRepository.deleteById(ruleName.getId());
        assertEquals(ruleNameRepository.findAll().size(), 0);
    }
}
