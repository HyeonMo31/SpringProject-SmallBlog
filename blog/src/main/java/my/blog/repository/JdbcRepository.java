package my.blog.repository;

import my.blog.domain.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

@org.springframework.stereotype.Repository
public class JdbcRepository implements Repository{

    private final JdbcTemplate template;

    public JdbcRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        String sql = "insert into POST values(?, ?, ?, ?, ?)";
        template.update(sql, post.getId(), post.getTitle(), post.getName(), post.getDate(), post.getContent());
        return post;
    }

    @Override
    public Post findById(Long id) {
        String sql = "select * from POST where id = ?";
        String toStringId = Long.toString(id);
        return template.queryForObject(sql, postRowMapper(), toStringId);
    }
    @Override
    public List<Post> findAll() {
        String sql = "select * from POST";
        return template.query(sql, postRowMapper());
    }
    @Override
    public void update(Long id, String title, String content) {
        String sql = "update POST set title = ?, content = ? where id = ?";
        template.update(sql, title, content, id);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from POST where id = ?";
        template.update(sql, id);
    }


    public void clear() {
        String sql = "delete from POST";
        template.update(sql);
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            Long id = Long.parseLong(rs.getString("id"));
            post.setId(id);
            post.setTitle(rs.getString("title"));
            post.setName(rs.getString("name"));
            post.setDate(rs.getString("date"));
            post.setContent(rs.getString("content"));
            return post;
        };
    }

}
