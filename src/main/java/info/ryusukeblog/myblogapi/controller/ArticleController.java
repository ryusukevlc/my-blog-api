package info.ryusukeblog.myblogapi.controller;

import info.ryusukeblog.myblogapi.controller.validator.ArticleControllerValidator;
import info.ryusukeblog.myblogapi.dto.ArticleDto;
import info.ryusukeblog.myblogapi.model.Article;
import info.ryusukeblog.myblogapi.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleControllerValidator validator;


    public ArticleController(ArticleService articleService, ArticleControllerValidator validator) {
        this.articleService = articleService;
        this.validator = validator;
    }

    @GetMapping("/articles")
    public List<ArticleDto> getColumnsWithPagination(@RequestParam(value = "limit", required = true, defaultValue = "0") int limit, @RequestParam(value = "offset", required = true, defaultValue = "0") int offset, @RequestParam(value = "fields", required = false, defaultValue = "") List<String> fields) {
        this.validator.validateRequestFields(Article.class, fields);
        return this.articleService.getColumnsWithPagination(limit, offset, fields);
    }

    @GetMapping("/articles/count")
    public Map<String, Long> getCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("allArticleNumbers", this.articleService.getCount());
        return response;
    }

    @GetMapping("/articles/{id}")
    public ArticleDto getOne(@PathVariable("id") int id, @RequestParam(value = "markdown", required = false, defaultValue = "false") boolean isMarkdown) {
        return this.articleService.getOne(id, isMarkdown);
    }

    @PostMapping("/articles")
    public ArticleDto save(@RequestBody ArticleDto articleDto) {
        return this.articleService.save(articleDto);
    }

    @PostMapping("/articles/drafts")
    public ArticleDto saveAsDraft(@RequestBody ArticleDto articleDto) {
        return this.articleService.saveAsDraft(articleDto);
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
