package info.ryusukeblog.myblogapi.article.repository;

import info.ryusukeblog.myblogapi.article.model.Article;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    List<Article> selectForPagination(int limit, int offset);

    List<Article> selectAll();
}
