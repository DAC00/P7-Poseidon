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

    /**
     * Get all the RuleName from de database.
     *
     * @return List of all the RuleName.
     */
    public List<RuleName> getRuleNames() {
        return ruleNameRepository.findAll();
    }

    /**
     * Get the RuleName with the id ruleNameId.
     *
     * @param ruleNameId of the RuleName to find.
     * @return RuleName with the id ruleNameId.
     */
    public Optional<RuleName> getRuleNameById(Integer ruleNameId) {
        return ruleNameRepository.findById(ruleNameId);
    }

    /**
     * Save the RuleName in the database.
     *
     * @param ruleNameToSave is the RuleName to save.
     */
    public void saveRuleName(RuleName ruleNameToSave) {
        ruleNameRepository.save(ruleNameToSave);
    }

    /**
     * Update the RuleName with id ruleNameId.
     *
     * @param ruleNameId      of the RuleName to update.
     * @param ruleNameUpdated is the RuleName with updated information.
     */
    public void updateRuleNameById(Integer ruleNameId, RuleName ruleNameUpdated) {
        Optional<RuleName> oldRuleName = getRuleNameById(ruleNameId);
        if (oldRuleName.isPresent() && ruleNameId.equals(ruleNameUpdated.getId())) {
            ruleNameRepository.save(ruleNameUpdated);
        }
    }

    /**
     * Delete the RuleName with id ruleNameId.
     *
     * @param ruleNameId of the RuleName to delete.
     */
    public void deleteRuleNameById(Integer ruleNameId) {
        ruleNameRepository.deleteById(ruleNameId);
    }
}
