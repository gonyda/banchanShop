package com.bbsk.banchanshop.cart.controller;

import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.cart.dto.InsertCartDto;
import com.bbsk.banchanshop.cart.dto.UserCartDto;
import com.bbsk.banchanshop.cart.dto.UserCartItemsDto;
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

        List<UserCartItemsDto> cartItems = new ArrayList<>();
        cartService.findAllByCartId(user.getUserId()).forEach(e -> {
            cartItems.add(
                UserCartItemsDto.builder()
                        .banchanId(e.getBanchan().getBanchanId())
                        .banchanName(e.getBanchan().getBanchanName())
                        .banchanPrice(e.getBanchan().getBanchanPrice())
                        .banchanTotalPrice(e.getBanchanTotalPrice())
                        .banchanQuantity(e.getBanchanQuantity())
                        .build()
            );
        });

        CartEntity cart = cartService.findByCartId(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cart", UserCartDto.builder()
                                                            .cartTotalQuantity(cart.getCartTotalQuantity())
                                                            .cartTotalPrice(cart.getCartTotalPrice())
                                                            .build()
                          );
        return "cart/cart";
    }

    @PostMapping("/insert-cart")
    public String postCart(InsertCartDto dto, @AuthenticationPrincipal UserEntity user, RedirectAttributes ra) {

        cartService.addBanchanInCart(user, banchanService.findBanchanById(dto.getBanchanId()), dto.getQuantity());

        ra.addFlashAttribute("postcart", true);

        return "redirect:/banchan/" + dto.getBanchanId();
    }
}
