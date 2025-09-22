package com.example.shop.sales;

import com.example.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
        CustomUser user = (CustomUser) auth.getPrincipal();
        // System.out.println(user.id);
        // sales.setMemberId(user.id);
        salesRepository.save(sales);

        return "redirect:/list/page/1";
    }

    @GetMapping("/order/all")
    public String getAllOrder() {
        List<Sales> result = salesRepository.findAll();
        System.out.println(result.get(0));
        return "redirect:/list/page/1";
    }
}
