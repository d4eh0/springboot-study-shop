package com.example.shop.sales;

import com.example.shop.member.CustomUser;
import com.example.shop.member.Member;
import com.example.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/order")
    public String saveOrder(Authentication auth,
                            String itemName,
                            Integer price,
                            Integer count
    ) {
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(itemName);
        CustomUser user = (CustomUser) auth.getPrincipal();
        System.out.println(user);
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);
        salesRepository.save(sales);

        return "redirect:/list/page/1";
    }

    @GetMapping("/order/all")
    public String getAllOrder(Model model) {
//        List<Sales> result = salesRepository.customFindAll();
//        List<SalesDto> a = new ArrayList<>();
//        for (Sales s : result) {
//            SalesDto dto = new SalesDto();
//            dto.itemName = s.getItemName();
//            dto.price = s.getPrice();
//            dto.username = s.getMember().getUsername();
//            a.add(dto);
//        }
        var result = memberRepository.findById(1L);
        System.out.println(result.get().getSales());

//        List<Sales> a = salesRepository.findAll();
//        System.out.println(a.get(0));
//        model.addAttribute("salesList", a); // 모델에 넣기
        return "sales.html";
    }
}

class SalesDto {
    public String itemName;
    public Integer price;
    public String username;
}