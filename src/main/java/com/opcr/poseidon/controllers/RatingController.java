package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.Rating;
import com.opcr.poseidon.services.RatingService;
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
public class RatingController {

    @Autowired
    private RatingService ratingService;

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @RequestMapping("/rating/list")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("ratings", ratingService.getRatings());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            logger.info("CREATE : Rating %s".formatted(model));
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.getRatingById(id);
        if (rating.isPresent()) {
            model.addAttribute("rating", rating.get());
            return "rating/update";
        }
        return "redirect:/rating/list";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        } else {
            ratingService.updateRatingById(id, rating);
            logger.info("UPDATE : Rating id %s".formatted(id));
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRatingById(id);
        logger.info("DELETE : Rating id %s".formatted(id));
        return "redirect:/rating/list";
    }
}
