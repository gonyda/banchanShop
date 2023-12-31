package com.bbsk.banchanshop.banchan.controller;

import com.bbsk.banchanshop.banchan.dto.ResponseBanchanDto;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/banchan")
public class BanchanController {

    private final BanchanService banchanService;

    @GetMapping("/{id}")
    public String getBanchan(@PathVariable("id") Long id, Model model) {

        model.addAttribute("banchan", new ResponseBanchanDto(banchanService.findBanchanById(id)));

        return "banchan/banchanDetail";
    }
}
