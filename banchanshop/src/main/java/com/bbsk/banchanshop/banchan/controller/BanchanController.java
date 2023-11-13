package com.bbsk.banchanshop.banchan.controller;

import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@RequestMapping("/banchan")
public class BanchanController {

    private final BanchanService banchanService;

    @GetMapping("/{id}")
    public String getBanchan(@PathVariable("id") Long id, @AuthenticationPrincipal UserEntity user, Model model) {

        model.addAttribute("userName", user.getUsername());
        model.addAttribute("banchan", banchanService.findBanchanById(id));

        return "banchan/banchanDetail";
    }
}
