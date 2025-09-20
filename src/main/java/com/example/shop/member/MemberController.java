package com.example.shop.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository; // 의존성주입~
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/list"; // 로그인 했으면 목록으로
        }
        return "register.html"; // 비로그인 사용자는 회원가입 가능
    }

    @PostMapping("/member")
    public String addMember(String username,
                            String password,
                            String displayName) {
        Member member = new Member();
        member.setUsername(username);
        // var hash = new BCryptPasswordEncoder().encode(password);
        var hash = passwordEncoder.encode(password);
        member.setPassword(hash);
        member.setDisplayName(displayName);
        memberRepository.save(member);
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login() {
        var result = memberRepository.findByUsername("eogud3332");
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        return "mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDTO getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        var data = new MemberDTO(result.getUsername(), result.getDisplayName());
        return data;
    }
}

class MemberDTO {
    public String username;
    public String displayName;

    MemberDTO(String a, String b) {
        this.username = a;
        this.displayName = b;
    }
}