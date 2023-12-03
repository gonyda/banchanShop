package com.bbsk.banchanshop;

import com.bbsk.banchanshop.banchan.dto.ResponseBanchanDto;
import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.contant.UserType;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final BanchanService banchanService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserEntity user, Model model, @RequestParam(defaultValue = "1") int page) {

        Page<BanchanEntity> paging = banchanService.findAllByPaging(page - 1); // 페이징

        List<ResponseBanchanDto> contents = new ArrayList<>();
        paging.getContent().forEach(e -> {
            contents.add(new ResponseBanchanDto(e.getBanchanId(), e.getBanchanName(), e.getBanchanPrice(), e.getBanchanStockQuantity()));
        });

        model.addAttribute("banchanList", paging.getContent());
        model.addAttribute("totalPage", paging.getTotalPages());

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
        user.updateRole(UserType.ROLE_USER);

        log.error(user.toString());

        userService.registUser(user);

        return "redirect:/login";
    }
}
