package my.blog.service;

import my.blog.domain.Post;
import my.blog.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final Repository repository;

    public PostService(Repository repository) {
        this.repository = repository;
    }

    public Post create(Post post) {
        repository.save(post);
        return post;
    }

    public List<Post> findList() {
        return repository.findAll();
    }

}
