package com.example.shop.item;

import com.example.shop.comment.Comment;
import com.example.shop.comment.CommentRepository;
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
    private final CommentRepository commentRepository;

    /* ====== 상품리스트 ====== */
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
        return "list.html";
    }

    /* ====== 상품 추가 ====== */
    @GetMapping("/write")
    public String write() {
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(String title, Integer price) {

        itemService.saveItem(title, price);
        return "redirect:/list";
    }

    /* ====== 상품 상세 ====== */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

            List<Comment> comments = commentRepository.findAllByParentId(id);
            model.addAttribute("comments", comments);
            Optional<Item> result = itemRepository.findById(id);
            if ( result.isPresent()) {
                model.addAttribute("item", result.get());
                return "detail.html";
            } else {
                return "redirect:/list";
            }
    }

    /* ====== 상품 편집 ====== */
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

    /* ====== 상품 삭제 ====== */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    /* ====== 상품 검색 ====== */
    @PostMapping("/search")
    public String postSearch(String searchText) {
        List<Item> result = itemRepository.findAllByTitleContains(searchText);
        System.out.println(result);
        return "redirect:/list/page/1";
    }
    // TODO: 컨트롤러 서비스 분리
}
