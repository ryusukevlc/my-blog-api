package info.ryusukeblog.myblogapi.article;

import info.ryusukeblog.myblogapi.article.model.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<Article> articles(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        return this.articleService.getArticlesForPagination(limit, offset);
    }
}
