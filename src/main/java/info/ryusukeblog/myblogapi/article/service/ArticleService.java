package info.ryusukeblog.myblogapi.article.service;

import info.ryusukeblog.myblogapi.article.dto.ArticleDto;
import info.ryusukeblog.myblogapi.article.model.Article;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getArticlesForPagination(int limit, int offset, List<String> fields);

    Article getArticleDetail(int id);

    Article save(Article article);

    Article update(Article article);

    void delete(int id);
}

