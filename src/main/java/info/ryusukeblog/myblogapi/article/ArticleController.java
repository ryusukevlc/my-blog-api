package info.ryusukeblog.myblogapi.article;

import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.model.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {

    @GetMapping("/articles")
    public List<Article> articles() {

         List<Article> articles = new ArrayList<>();
         List<Tag> tags = new ArrayList<>();
         tags.add(new Tag("テストタグ1"));
         tags.add(new Tag("テストタグ2"));
         articles.add(new Article("テストタイトル","テスト内容", "テスト内容の一部", tags));

         return articles;
    }
}
