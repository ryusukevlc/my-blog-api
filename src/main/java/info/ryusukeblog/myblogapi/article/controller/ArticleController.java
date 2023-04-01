package info.ryusukeblog.myblogapi.article.controller;

import info.ryusukeblog.myblogapi.article.dto.ArticleDto;
import info.ryusukeblog.myblogapi.article.dto.ArticleMapper;
import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    ArticleService articleService;

    ArticleMapper articleMapper;

    public ArticleController(ArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }

    @GetMapping("/articles")
    public List<Article> articles(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        return this.articleService.getArticlesForPagination(limit, offset);
    }

    @GetMapping("/articles/{id}")
    public Article articleDetail(@PathVariable("id") int id) {
        return this.articleService.getArticleDetail(id);
    }

    @PostMapping("articles")
    public void createArticle(@RequestBody ArticleDto articleDto) {
        Article article = this.articleMapper.getArticleFromDto(articleDto);
        this.articleService.save(article);
    }

}
