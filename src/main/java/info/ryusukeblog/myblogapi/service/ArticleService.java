package info.ryusukeblog.myblogapi.service;

import info.ryusukeblog.myblogapi.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getArticlesForPagination(int limit, int offset, List<String> fields);

    long getArticleCount();

    ArticleDto getArticle(int id, boolean isMarkdown);

    ArticleDto save(ArticleDto articleDto);

    ArticleDto update(ArticleDto articleDto);

    boolean delete(int id);
}

