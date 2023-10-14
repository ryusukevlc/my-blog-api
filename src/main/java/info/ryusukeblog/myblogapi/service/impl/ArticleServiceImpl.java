package info.ryusukeblog.myblogapi.service.impl;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import info.ryusukeblog.myblogapi.dto.ArticleDto;
import info.ryusukeblog.myblogapi.model.Article;
import info.ryusukeblog.myblogapi.repository.ArticleMapper;
import info.ryusukeblog.myblogapi.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";
    private static final String ARTICLE_NOT_FOUND_WITH_ID = "記事が見つかりませんでした。(記事ID: %s)";
    private static final String COULD_NOT_SAVE_ARTICLE_WITH_TITLE = "記事を保存できませんでした。(記事タイトル: %s)";
    private static final String COULD_NOT_UPDATE_ARTICLE_WITH_ID = "記事を更新できませんでした。(記事ID: %s)";
    private final ArticleMapper articleMapper;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, ModelMapper modelMapper) {
        this.articleMapper = articleMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArticleDto> getArticlesForPagination(int limit, int offset) {
        System.out.println(this.articleMapper.findWithPagination(limit, offset));
        return this.modelMapper.map(this.articleMapper.findWithPagination(limit, offset), new TypeToken<List<ArticleDto>>() {
        }.getType());
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
    public ArticleDto save(ArticleDto articleDto) throws ResponseStatusException {
        return this.saveArticle(articleDto, INSERT);
    }

    @Override
    public ArticleDto update(ArticleDto articleDto) throws ResponseStatusException {
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

    private ArticleDto saveArticle(ArticleDto articleDto, String type) throws ResponseStatusException {
        // TODO: このメソッドはトランザクション化した方がいい。理由は複数テーブル更新するため、最後のテーブル更新時に例外が発生するとデータに不整合が発生するため。

        Article article = this.modelMapper.map(articleDto, Article.class);

        if (INSERT.equals(type)) {

            boolean hasSaved = this.articleMapper.save(article);
            if (!hasSaved) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(COULD_NOT_SAVE_ARTICLE_WITH_TITLE, article.getTitle()));
            }
        } else if (UPDATE.equals(type)) {

            if (this.articleMapper.findById(article.getId()) == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ARTICLE_NOT_FOUND_WITH_ID, article.getId()));
            }

            boolean hasUpdated = this.articleMapper.update(article);
            if (!hasUpdated) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(COULD_NOT_UPDATE_ARTICLE_WITH_ID, article.getId()));
            }
        }

        if (UPDATE.equals(type)) {
            this.articleMapper.deleteArticleTagRelationByArticleId(article.getId());
        }
        if (article.getTagList().isEmpty()) {
            return articleDto;
        }
        if (INSERT.equals(type)) {
            article.setId(this.articleMapper.getLastInsertId());
        }
        this.articleMapper.saveArticleTagRelation(article);

        return articleDto;
    }
}
