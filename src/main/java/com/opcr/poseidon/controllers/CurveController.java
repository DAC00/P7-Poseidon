package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.CurvePoint;
import com.opcr.poseidon.services.CurvePointService;
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
public class CurveController {

    @Autowired
    CurvePointService curvePointService;

    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            logger.info("CREATE : CurvePoint %s".formatted(model));
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
        if (curvePoint.isPresent()) {
            model.addAttribute("curvePoint", curvePoint.get());
            return "curvePoint/update";
        }
        return "redirect:/curvePoint/list";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        } else {
            curvePointService.updateCurvePointById(id, curvePoint);
            logger.info("UPDATE : CurvePoint id %s".formatted(id));
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePointById(id);
        logger.info("DELETE : CurvePoint id %s".formatted(id));
        return "redirect:/curvePoint/list";
    }
}
