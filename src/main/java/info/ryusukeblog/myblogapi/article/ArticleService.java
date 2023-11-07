package info.ryusukeblog.myblogapi.article;

import info.ryusukeblog.myblogapi.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getColumnsWithPagination(int limit, int offset, List<String> fields);

    long getCount();

    ArticleDto getOne(int id, boolean isMarkdown);

    ArticleDto save(ArticleDto articleDto);

    ArticleDto saveAsDraft(ArticleDto articleDto);

    ArticleDto update(ArticleDto articleDto);

    void delete(int id);
}

