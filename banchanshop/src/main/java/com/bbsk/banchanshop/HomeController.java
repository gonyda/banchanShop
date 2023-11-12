package com.bbsk.banchanshop;

import com.bbsk.banchanshop.contant.UserType;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/sign-up")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup-process")
    public String signupprocess(UserEntity user) {
        log.error("회원가입 로직 실행");
        user.isAdmin(UserType.N);

        log.error(user.toString());

        userService.registUser(user);

        return "redirect:/login";
    }
}
