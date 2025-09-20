package com.example.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository; // 리포지토리 등록
    private final ItemService itemService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/list/page/{pageNumber}")
    public String getListPage(Model model, @PathVariable int pageNumber) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(pageNumber - 1, 3));
        model.addAttribute("items", result);
        model.addAttribute("currentPage", result.getNumber() + 1);
        model.addAttribute("totalPage", (int) result.getTotalPages() + 1);
        System.out.println("totalPages = " + result.getTotalPages());
        System.out.println("totalPages = " + (int) result.getTotalPages());
        return "list.html";
    }

    @GetMapping("/write")
    public String write() {
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(String title, Integer price) {

        itemService.saveItem(title, price);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        // try {
            // throw new Exception("안녕 나는 에러");
            Optional<Item> result = itemRepository.findById(id);
            if ( result.isPresent()) {
                model.addAttribute("item", result.get());
                return "detail.html";
            } else {
                return "redirect:/list";
            }
        // } catch (Exception e) {
            // System.out.println(e.getMessage());
            // return ResponseEntity.status(400).body("에러남 ㅅㄱ");
        // }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        if ( result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit")
    public String editItem(String title, Integer price, Long id) {

        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);

        return "redirect:/list";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }
    // 컨트롤러 서비스 분리
    // 제목 너무 길거나 음수면 예외처리
}
