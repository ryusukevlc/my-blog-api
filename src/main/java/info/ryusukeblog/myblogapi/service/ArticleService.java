package info.ryusukeblog.myblogapi.service;

import info.ryusukeblog.myblogapi.dto.ArticleDto;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getArticlesForPagination(int limit, int offset);

    long getArticleCount();

    ArticleDto getArticle(int id, boolean isMarkdown);

    ArticleDto save(ArticleDto articleDto) throws ResponseStatusException;

    ArticleDto update(ArticleDto articleDto) throws ResponseStatusException;

    boolean delete(int id);
}

