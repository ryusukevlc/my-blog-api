package info.ryusukeblog.myblogapi.article.controller;

import info.ryusukeblog.myblogapi.article.dto.ArticleDto;
import info.ryusukeblog.myblogapi.article.dto.ArticleMapper;
import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ArticleDto> getArticles(@RequestParam("limit") int limit, @RequestParam("offset") int offset, @RequestParam(value = "fields", defaultValue = "") List<String> fields) {
        new Article().validateFieldNames(fields);
        return this.articleService.getArticlesForPagination(limit, offset, fields);
    }

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable("id") int id) {
        return this.articleService.getArticleDetail(id);
    }

    @PostMapping("/articles")
    public Article create(@RequestBody ArticleDto articleDto) {
        // バリデーションの意味でDTOとして受け取ってからModelに渡している。もし不正な値が渡された場合はModelでバリデートして例外を送出する。
        Article article = this.articleMapper.getArticleFromDtoForCreate(articleDto);
        return this.articleService.save(article);
    }

    @PatchMapping("/articles")
    public Article update(@RequestBody ArticleDto articleDto) {
        // バリデーションの意味でDTOとして受け取ってからModelに渡している。もし不正な値が渡された場合はModelでバリデートして例外を送出する。
        Article article = this.articleMapper.getArticleFromDtoForUpdate(articleDto);
        return this.articleService.update(article);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        this.articleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
