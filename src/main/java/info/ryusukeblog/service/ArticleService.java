package info.ryusukeblog.service;

import info.ryusukeblog.dto.ArticleDto;
import info.ryusukeblog.model.models.Article;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getArticlesForPagination(int limit, int offset, List<String> fields);

    int getArticleCount();

    ArticleDto getArticleDetail(int id, List<String> fields, boolean isMarkdown);

    ArticleDto save(Article article);

    ArticleDto update(Article article);

    void delete(int id);
}

