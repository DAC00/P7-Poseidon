package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.BidList;
import com.opcr.poseidon.services.BidListService;
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
public class BidListController {

    @Autowired
    private BidListService bidListService;

    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @RequestMapping("/bidList/list")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("bidLists", bidListService.getBidLists());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.saveBidList(bid);
            logger.info("CREATE : BidList %s".formatted(model));
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bidList = bidListService.getBidListById(id);
        if (bidList.isPresent()) {
            model.addAttribute("bidList", bidList.get());
            return "bidList/update";
        }
        return "redirect:/bidList/list";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        } else {
            bidListService.updateBidListById(id, bidList);
            logger.info("UPDATE : BidList id %s".formatted(id));
            return "redirect:/bidList/list";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidListById(id);
        logger.info("DELETE : BidList id %s".formatted(id));
        return "redirect:/bidList/list";
    }
}
