package com.opcr.poseidon.services;

import com.opcr.poseidon.domain.RuleName;
import com.opcr.poseidon.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> getRuleNames() {
        return ruleNameRepository.findAll();
    }

    public Optional<RuleName> getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public void saveRuleName(RuleName ruleNameToSave) {
        ruleNameRepository.save(ruleNameToSave);
    }

    public void updateRuleNameById(Integer idRuleName, RuleName ruleNameUpdated) {
        Optional<RuleName> oldRuleName = getRuleNameById(idRuleName);
        if (oldRuleName.isPresent() && idRuleName.equals(ruleNameUpdated.getId())) {
            ruleNameRepository.save(ruleNameUpdated);
        }
    }

    public void deleteRuleNameById(Integer idRuleName) {
        ruleNameRepository.deleteById(idRuleName);
    }
}
