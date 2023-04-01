package info.ryusukeblog.myblogapi.article.dto;

import info.ryusukeblog.myblogapi.article.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    /**
     * 返却用
     *
     * @param article
     * @return
     */
    public ArticleDto getDtoFromArticle(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.id = article.getId();
        articleDto.title = article.getTitle();
        articleDto.content = article.getContent();
        articleDto.isWriting = article.getIsWriting();
        articleDto.partOfContent = article.getPartOfContent();
        articleDto.createdAt = article.getCreatedAt();
        articleDto.updatedAt = article.getUpdatedAt();
        return articleDto;
    }

    /**
     * POST用
     *
     * @param articleDto
     * @return
     */
    public Article getArticleFromDto(ArticleDto articleDto) {
        return new Article(
                articleDto.title,
                articleDto.content,
                articleDto.tagList
        );
    }
}
