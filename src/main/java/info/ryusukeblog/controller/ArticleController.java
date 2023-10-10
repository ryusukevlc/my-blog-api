package info.ryusukeblog.controller;

import info.ryusukeblog.dto.ArticleDto;
import info.ryusukeblog.service.ArticleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {

    ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<ArticleDto> getArticles(@RequestParam(value = "limit", required = true, defaultValue = "0") int limit, @RequestParam(value = "offset", required = true, defaultValue = "0") int offset) {
        return this.articleService.getArticlesForPagination(limit, offset);
    }

    @GetMapping("/articles/count")
    public Map<String, Long> getArticleCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("allArticleNumbers", this.articleService.getArticleCount());
        return response;
    }

    @GetMapping("/articles/{id}")
    public ArticleDto getArticle(@PathVariable("id") int id, @RequestParam(value = "markdown", required = false, defaultValue = "false") boolean isMarkdown) {
        return this.articleService.getArticle(id, isMarkdown);
    }

    @PostMapping("/articles")
    public ArticleDto create(@RequestBody ArticleDto articleDto) {
        return this.articleService.save(articleDto);
    }

    @PatchMapping("/articles")
    public ArticleDto update(@RequestBody ArticleDto articleDto) {
        return this.articleService.update(articleDto);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        boolean hasDeleted = this.articleService.delete(id);
        if (hasDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
