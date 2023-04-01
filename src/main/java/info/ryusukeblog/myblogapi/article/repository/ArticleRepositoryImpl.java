package info.ryusukeblog.myblogapi.article.repository;

import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.model.Tag;
import info.ryusukeblog.myblogapi.article.repository.extractor.ArticlesExtractor;
import info.ryusukeblog.myblogapi.article.repository.extractor.TagsExtractor;
import info.ryusukeblog.myblogapi.article.repository.rowmapper.ArticleRowMapper;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final int EXCPECTED_UPDATE_NUMBER = 1;

    public ArticleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {

        String sqlForArticles = "insert into articles (title, content, part_of_content, is_writing) values (?, ?, ?, ?)";
        String sqlForGettingIdLatestArticle = "select last_insert_id()";

        this.jdbcTemplate.update(sqlForArticles, article.getTitle(), article.getContent(), article.getPartOfContent(), article.getIsWriting());
        int articleId = this.jdbcTemplate.queryForObject(sqlForGettingIdLatestArticle, Integer.class);
        this.saveArticlesTags(articleId, article.getTagList());

        return this.selectForArticleDetail(articleId);
    }

    @Override
    public List<Article> selectForPagination(int limit, int offset) {
        String sql = "select id, title, part_of_content, created_at from articles where is_writing = 0 order by created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new ArticlesExtractor(), limit, offset);
    }

    @Override
    public Article selectForArticleDetail(int id) {

        String sql_article = "select id, title, content, created_at, updated_at from articles where id = ?";
        String sql_tags = "select tags.id, tags.name, tags.created_at, tags.updated_at from articles_tags left join tags on articles_tags.tag_id=tags.id where articles_tags.article_id in (?);";

        Article article = this.jdbcTemplate.queryForObject(sql_article, new ArticleRowMapper(), id);
        List<Tag> tags = this.jdbcTemplate.query(sql_tags, new TagsExtractor(), id);

        article.setTagList(tags);
        return article;
    }

    @Override
    public Article update(Article article) {

        String sqlForArticles = "update articles set title = ?, content = ?, part_of_content = ? where id = ?";
        String sqlForDeleteArticlesTags = "delete from articles_tags where id = ?";

        int updateCount = this.jdbcTemplate.update(sqlForArticles, article.getTitle(), article.getContent(), article.getPartOfContent(), article.getId());
        if (updateCount != EXCPECTED_UPDATE_NUMBER) {
            // TODO: 実際に発行されるSQLをログに出力するようにする。
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sqlForArticles, EXCPECTED_UPDATE_NUMBER, updateCount);
        }
        this.jdbcTemplate.update(sqlForDeleteArticlesTags, article.getId());
        this.saveArticlesTags(article.getId(), article.getTagList());

        return this.selectForArticleDetail(article.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from articles where id = ?";
        this.jdbcTemplate.update(sql, id);
    }

    private void saveArticlesTags(int articleId, List<Tag> tagList) {
        String sqlForArticlesTags = "insert into articles_tags (article_id, tag_id) values (?, ?)";
        List<Object[]> articleIdAndTagIdList = new ArrayList<>();
        for (Tag tag : tagList) {
            articleIdAndTagIdList.add(new Object[]{
                    articleId,
                    tag.getId()
            });
        }
        this.jdbcTemplate.batchUpdate(sqlForArticlesTags, articleIdAndTagIdList);
    }

}
