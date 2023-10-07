package info.ryusukeblog.article.service;

import info.ryusukeblog.article.dto.ArticleDto;
import info.ryusukeblog.article.model.Article;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getArticlesForPagination(int limit, int offset, List<String> fields);

    int getArticleCount();

    ArticleDto getArticleDetail(int id, List<String> fields, boolean isMarkdown);

    ArticleDto save(Article article);

    ArticleDto update(Article article);

    void delete(int id);
}

