package info.ryusukeblog.myblogapi.article.repository;

import info.ryusukeblog.myblogapi.article.dto.ArticleDto;
import info.ryusukeblog.myblogapi.article.model.Article;

import java.util.List;

public interface ArticleRepository {

    ArticleDto save(Article article);

    List<ArticleDto> selectForPagination(int limit, int offset, List<String> fields);

    int getArticleCount();

    ArticleDto selectForArticleDetail(int id, List<String> fields);

    ArticleDto update(Article article);

    void delete(int id);
}
