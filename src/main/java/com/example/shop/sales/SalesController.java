package com.example.shop.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesRepository salesRepository;

    @PostMapping("/order")
    public String saveOrder(Authentication auth, String itemName, Integer price, Integer count) {
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(itemName);
        User user = (User) auth.getPrincipal();
        System.out.println(user);
        // sales.setMemberId();
        // salesRepository.save(sales);

        return "list.html";
    }
}
