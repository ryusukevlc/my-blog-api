package info.ryusukeblog.service.impl;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;

import info.ryusukeblog.repository.ArticleMapper;
import info.ryusukeblog.dto.ArticleDto;
import info.ryusukeblog.model.Article;
import info.ryusukeblog.service.ArticleService;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";

    private final ArticleMapper articleMapper;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, ModelMapper modelMapper) {
        this.articleMapper = articleMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArticleDto> getArticlesForPagination(int limit, int offset) {
        System.out.println(this.articleMapper.findWithPagination(limit, offset));
        return this.modelMapper.map(this.articleMapper.findWithPagination(limit, offset), new TypeToken<List<ArticleDto>>() {}.getType());
    }

    @Override
    public long getArticleCount() {
        return this.articleMapper.count();
    }

    @Override
    public ArticleDto getArticle(int id, boolean isMarkdown) {
        ArticleDto articleDto = this.modelMapper.map(this.articleMapper.findById(id), ArticleDto.class);
        if (!isMarkdown) {
            this.convertToHtml(articleDto);
        }
        return articleDto;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        return this.saveArticle(articleDto, INSERT);
    }

    @Override
    public ArticleDto update(ArticleDto articleDto) {
        return this.saveArticle(articleDto, UPDATE);
    }

    @Override
    public boolean delete(int id) {
        boolean hasDeleted = this.articleMapper.delete(id);
        hasDeleted = this.articleMapper.deleteArticleTagRelationByArticleId(id);
        return hasDeleted;
    }

    private void convertToHtml(ArticleDto articleDto) {
        Parser parser = Parser.builder().build();
        Document document = parser.parse(articleDto.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        articleDto.setContent(renderer.render(document));
    }

    private ArticleDto saveArticle(ArticleDto articleDto, String type) {

        Article article = this.modelMapper.map(articleDto, Article.class);

        boolean hasSaved = false;

        if (INSERT.equals(type)) {
            hasSaved = this.articleMapper.save(article);
        } else if (UPDATE.equals(type)) {
            hasSaved = this.articleMapper.update(article);
        }

        if (!hasSaved) {
            logger.error("記事を保存できませんでした。");
        }

         if (article.getTagList().isEmpty()) {
            return articleDto;
        }

        if (INSERT.equals(type)) {
            article.setId(this.articleMapper.getLastInsertId());
        } else if (UPDATE.equals(type)) {
            this.articleMapper.deleteArticleTagRelationByArticleId(article.getId());
        }

        this.articleMapper.saveArticleTagRelation(article);
        return articleDto;
    }
}
