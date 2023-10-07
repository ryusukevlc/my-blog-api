package info.ryusukeblog.article.dto;

import info.ryusukeblog.article.model.Article;
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
    public Article getArticleFromDtoForCreate(ArticleDto articleDto) {
        return new Article(
                articleDto.title,
                articleDto.content,
                articleDto.tagList
        );
    }

    /**
     * PATCH用
     *
     * @param articleDto
     * @return
     */
    public Article getArticleFromDtoForUpdate(ArticleDto articleDto) {
        return new Article(
                articleDto.id,
                articleDto.title,
                articleDto.content,
                articleDto.tagList
        );
    }
}
