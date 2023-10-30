package my.blog.repository;
import my.blog.domain.Post;

import java.util.List;
public interface Repository {
    Post save(Post post);
    Post findById(Long id);
    List<Post> findAll();
    void update(Long id, Post post);
    void delete(Long id);

}
