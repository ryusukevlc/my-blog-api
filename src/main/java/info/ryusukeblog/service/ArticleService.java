package info.ryusukeblog.service;

import java.util.List;

import info.ryusukeblog.dto.ArticleDto;

public interface ArticleService {

    List<ArticleDto> getArticlesForPagination(int limit, int offset);

    long getArticleCount();

    ArticleDto getArticle(int id, boolean isMarkdown);

    ArticleDto save(ArticleDto articleDto);

    ArticleDto update(ArticleDto articleDto);

    boolean delete(int id);
}

