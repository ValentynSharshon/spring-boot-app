package com.gmail.woosay333.springbootapp.controller;

import com.gmail.woosay333.springbootapp.model.Post;
import com.gmail.woosay333.springbootapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();

        model.addAttribute("title", "Blog page");
        model.addAttribute("posts", posts);

        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Create post page");

        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@ModelAttribute("post") Post post) {
        postRepository.save(post);

        return "redirect:/blog";
    }

}
