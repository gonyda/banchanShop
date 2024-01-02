package com.bbsk.banchanshop.user.controller;

import com.bbsk.banchanshop.user.dto.RequestUserDto;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*
* */import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal UserEntity user, Model model) {

        model.addAttribute("user", userService.findUserById(user.getUserId()));

        return "user/mypage";
    }

    @PostMapping("/{id}")
    public String updateUser(RequestUserDto requestUserDto, RedirectAttributes ra) {

        log.info(requestUserDto.toString());
        userService.updateUser(requestUserDto);

        ra.addFlashAttribute("isUpdate", true);

        return "redirect:/user/mypage";
    }
}
