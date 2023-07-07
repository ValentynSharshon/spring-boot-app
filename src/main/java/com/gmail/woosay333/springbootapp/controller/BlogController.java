package com.gmail.woosay333.springbootapp.controller;

import com.gmail.woosay333.springbootapp.model.Post;
import com.gmail.woosay333.springbootapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        List<Post> result = new ArrayList<>();

        post.ifPresent(result::add);
        model.addAttribute("title", "Post details page");
        model.addAttribute("post", result);

        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        List<Post> result = new ArrayList<>();

        post.ifPresent(result::add);
        model.addAttribute("title", "Post edit page");
        model.addAttribute("post", result);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@ModelAttribute("post") Post post) {
        Post oldPost = postRepository.findById(post.getId()).orElseThrow();
        oldPost.setTitle(post.getTitle());
        oldPost.setAnons(post.getAnons());
        oldPost.setFullText(post.getFullText());

        postRepository.save(oldPost);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).get();
        postRepository.delete(post);

        return "redirect:/blog";
    }

}
