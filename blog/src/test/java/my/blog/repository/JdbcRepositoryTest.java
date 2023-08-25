package my.blog.repository;

import com.zaxxer.hikari.HikariDataSource;
import my.blog.connection.ConnectionConst;
import my.blog.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class JdbcRepositoryTest {

    JdbcRepository jdbcRepository;

    public JdbcRepositoryTest() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        jdbcRepository = new JdbcRepository(dataSource);
    }


    @AfterEach
    void clearAll() {
        jdbcRepository.clear();
    }

    @Test
    void save() {
        Post post = new Post(1L, "title", "hyeonMo", "2023-08-25", "아무 내용");

        Post savedPost = jdbcRepository.save(post);
        Post findPost = jdbcRepository.findById(post.getId());

        Assertions.assertThat(findPost).isEqualTo(savedPost);

    }

    @Test
    void findAll() {
        Post post = new Post(1L, "title", "hyeonMo", "2023-08-25", "아무 내용");
        Post post2 = new Post(2L, "title", "hyeonMo", "2023-08-25", "아무 내용");

        jdbcRepository.save(post);
        jdbcRepository.save(post2);

        List<Post> findPost = jdbcRepository.findAll();
        Assertions.assertThat(findPost.size()).isEqualTo(2);

    }

    @Test
    void update() {
        Post post = new Post(1L, "title", "hyeonMo", "2023-08-25", "아무 내용");
        jdbcRepository.save(post);
        jdbcRepository.update(1L, "수정된 타이틀", "내용 바꿈");

        Post updatedPost = jdbcRepository.findById(1L);
        Assertions.assertThat(updatedPost.getTitle()).isEqualTo("수정된 타이틀");
        Assertions.assertThat(updatedPost.getContent()).isEqualTo("내용 바꿈");

    }

    @Test
    void delete() {
        Post post = new Post(1L, "title", "hyeonMo", "2023-08-25", "아무 내용");
        jdbcRepository.save(post);
        jdbcRepository.delete(post.getId());

        assertThatThrownBy(() -> jdbcRepository.findById(post.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);

    }
}