package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.Trade;
import com.opcr.poseidon.services.TradeService;
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
public class TradeController {

    @Autowired
    private TradeService tradeService;

    private static final Logger logger = LogManager.getLogger(TradeController.class);

    @RequestMapping("/trade/list")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("trades", tradeService.getTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.saveTrade(trade);
            logger.info("CREATE : Trade %s".formatted(trade));
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> trade = tradeService.getTradeById(id);
        if (trade.isPresent()) {
            model.addAttribute("trade", trade.get());
            return "trade/update";
        }
        return "redirect:/trade/list";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        } else {
            tradeService.updateTradeById(id, trade);
            logger.info("UPDATE : Trade id %s".formatted(id));
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTradeById(id);
        logger.info("DELETE : Trade id %s".formatted(id));
        return "redirect:/trade/list";
    }
}
