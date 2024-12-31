package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.RuleName;
import com.opcr.poseidon.services.RuleNameService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("ruleNames", ruleNameService.getRuleNames());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            logger.debug("CREATE : RuleName %s".formatted(model));
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> ruleName = ruleNameService.getRuleNameById(id);
        if (ruleName.isPresent()) {
            model.addAttribute("ruleName", ruleName.get());
            return "ruleName/update";
        }
        return "redirect:/ruleName/list";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        } else {
            ruleNameService.updateRuleNameById(id, ruleName);
            logger.debug("UPDATE : RuleName id %s".formatted(id));
            return "redirect:/ruleName/list";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleNameById(id);
        logger.debug("DELETE : RuleName id %s".formatted(id));
        return "redirect:/ruleName/list";
    }
}
