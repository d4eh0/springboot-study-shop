package com.example.shop;

import com.example.shop.item.Item;
import com.example.shop.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasicController {
    private final ItemRepository itemRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);
        return "redirect:/list/page/1";
    }

    @GetMapping("/date")
    @ResponseBody
    String date() {
        return ZonedDateTime.now().toString();
    }
}