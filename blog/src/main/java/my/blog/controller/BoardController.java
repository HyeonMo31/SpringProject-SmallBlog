package my.blog.controller;

import com.zaxxer.hikari.HikariDataSource;
import my.blog.connection.ConnectionConst;
import my.blog.domain.Post;
import my.blog.repository.JdbcRepository;
import my.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostService service;

    public BoardController() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        JdbcRepository jdbcRepository = new JdbcRepository(dataSource);
        this.service = new PostService(jdbcRepository);
    }

    @GetMapping
    public String load(Model model) {
        List<Post> items = service.findList();
        model.addAttribute("items", items);
        return "/board/main";
    }

}
