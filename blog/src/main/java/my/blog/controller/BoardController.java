package my.blog.controller;

import com.zaxxer.hikari.HikariDataSource;
import my.blog.connection.ConnectionConst;
import my.blog.domain.Post;
import my.blog.repository.JdbcRepository;
import my.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String mainListLoad(Model model) {
        List<Post> items = service.findAllList();
        model.addAttribute("items", items);
        return "/board/lists";
    }

    @GetMapping("/create")
    public String createFormLoad() {
        return "/board/createForm";
    }

    @PostMapping("/create")
    public String createPost(Post post, RedirectAttributes redirectAttributes){
        Post savedPost = service.create(post);
        redirectAttributes.addAttribute("id", savedPost.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/board/list/{id}";

    }

    @GetMapping("/list/{id}")
    public String oneListLoad(@PathVariable Long id, Model model){
        Post post = service.readById(id);
        System.out.println("post int oneListLoad = " + post);
        model.addAttribute("item", post);
        return "/board/list";
    }

    @GetMapping("/list/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        Post post = service.readById(id);
        model.addAttribute("item", post);
        return "/board/editForm";
    }

    @PostMapping("/list/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute  Post post){
        System.out.println(post);
        service.update(id, post);
        System.out.println(service.readById(id));
        return "redirect:/board/list/{id}";
    }



}
