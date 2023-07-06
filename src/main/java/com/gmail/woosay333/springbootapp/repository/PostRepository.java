package com.gmail.woosay333.springbootapp.repository;

import com.gmail.woosay333.springbootapp.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
