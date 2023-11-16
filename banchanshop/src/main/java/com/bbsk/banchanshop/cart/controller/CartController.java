package com.bbsk.banchanshop.cart.controller;

import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.cart.dto.RequestCartItemDto;
import com.bbsk.banchanshop.cart.dto.ResponseUserCartDto;
import com.bbsk.banchanshop.cart.service.CartService;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String postCart(RequestCartItemDto dto, @AuthenticationPrincipal UserEntity user, RedirectAttributes ra) {

        cartService.addBanchanInCart(user, banchanService.findBanchanById(dto.getBanchanId()), dto.getQuantity());

        ra.addFlashAttribute("postcart", true);

        return "redirect:/banchan/" + dto.getBanchanId();
    }

    @DeleteMapping("/cartitem/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") Long cartItemId, @AuthenticationPrincipal UserEntity user) {
        try {
            cartService.deleteCartItem(user, cartItemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/cartitem/{id}/{quantity}")
    public ResponseEntity<?> updateQuantity(@PathVariable("id") Long banchanId, @PathVariable("quantity") int quantity
            , @AuthenticationPrincipal UserEntity user) {
        try {
            cartService.addBanchanInCart(user, banchanService.findBanchanById(banchanId), quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
