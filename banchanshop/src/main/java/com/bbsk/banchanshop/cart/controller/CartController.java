package com.bbsk.banchanshop.cart.controller;

import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.cart.dto.RequestInsertCartDto;
import com.bbsk.banchanshop.cart.dto.ResponseUserCartDto;
import com.bbsk.banchanshop.cart.dto.ResponseUserCartItemsDto;
import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.service.CartService;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final BanchanService banchanService;
    
    @GetMapping("/cart-list")
    public String getCart(@AuthenticationPrincipal UserEntity user, Model model) {
        model.addAttribute("cart", new ResponseUserCartDto().toDto(cartService.findByCartId(user.getUserId())));

        return "cart/cart";
    }

    @PostMapping("/insert-cart")
    public String postCart(RequestInsertCartDto dto, @AuthenticationPrincipal UserEntity user, RedirectAttributes ra) {

        cartService.addBanchanInCart(user, banchanService.findBanchanById(dto.getBanchanId()), dto.getQuantity());

        ra.addFlashAttribute("postcart", true);

        return "redirect:/banchan/" + dto.getBanchanId();
    }
}
