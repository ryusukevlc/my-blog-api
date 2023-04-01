package info.ryusukeblog.myblogapi.article.service;

import info.ryusukeblog.myblogapi.article.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getArticlesForPagination(int limit, int offset);

    Article getArticleDetail(int id);

    Article save(Article article);

    Article saveArticle(Article article);

    void delete(int id);
}

