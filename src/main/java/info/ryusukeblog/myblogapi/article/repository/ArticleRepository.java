package info.ryusukeblog.myblogapi.article.repository;

import info.ryusukeblog.myblogapi.article.dto.ArticleDto;
import info.ryusukeblog.myblogapi.article.model.Article;

import java.util.List;

public interface ArticleRepository {

    Article save(Article article);

    List<ArticleDto> selectForPagination(int limit, int offset, List<String> fields);

    int getArticleCount();

    Article selectForArticleDetail(int id);

    Article update(Article article);

    void delete(int id);
}
