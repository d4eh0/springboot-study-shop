package com.example.shop. comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/comment")
    public String postComment(String content, Long parentId, Authentication auth) {
        User user = (User) auth.getPrincipal();
        System.out.println(user);
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUsername(user.getUsername());
            comment.setParentId(parentId);
            commentRepository.save(comment);
        return "redirect:/detail/" + parentId;
    }
}
