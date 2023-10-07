package info.ryusukeblog.repository.impl;

import info.ryusukeblog.repository.ArticleRepository;
import info.ryusukeblog.repository.extractor.ArticlesExtractor;
import info.ryusukeblog.dto.ArticleDto;
import info.ryusukeblog.model.models.Article;
import info.ryusukeblog.model.models.Tag;
import info.ryusukeblog.repository.extractor.TagsExtractor;
import info.ryusukeblog.repository.rowmapper.ArticleRowMapper;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final int EXCPECTED_UPDATE_NUMBER = 1;

    public ArticleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ArticleDto save(Article article) {

        String sqlForArticles = "insert into articles (title, content, part_of_content, is_writing) values (?, ?, ?, ?)";
        String sqlForGettingIdLatestArticle = "select last_insert_id()";

        this.jdbcTemplate.update(sqlForArticles, article.getTitle(), article.getContent(), article.getPartOfContent(), article.getIsWriting());
        int articleId = this.jdbcTemplate.queryForObject(sqlForGettingIdLatestArticle, Integer.class);
        this.saveArticlesTags(articleId, article.getTagList());

        return this.selectForArticleDetail(articleId, null);
    }

    @Override
    public List<ArticleDto> selectForPagination(int limit, int offset, List<String> fields) {
        String joinedFields;
        if (Objects.isNull(fields) || fields.isEmpty()) {
            joinedFields = "*";
        } else {
            joinedFields = String.join(",", fields);
        }
        String sql = "select " + joinedFields + " from articles where is_writing = 0 order by created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new ArticlesExtractor(fields), limit, offset);
    }

    @Override
    public int getArticleCount() {
        String sql = "select count(*) from articles";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public ArticleDto selectForArticleDetail(int id, List<String> fields) {
        String joinedFields;

        if (Objects.isNull(fields)) {
            fields = new ArrayList<>();
        }

        if (fields.isEmpty()) {
            joinedFields = "*";
        } else {
            joinedFields = String.join(",", fields);
        }
        String sql_article = "select " + joinedFields + " from articles where id = ?";
        String sql_tags = "select tags.id, tags.name, tags.created_at, tags.updated_at from articles_tags left join tags on articles_tags.tag_id=tags.id where articles_tags.article_id in (?);";

        ArticleDto article = this.jdbcTemplate.queryForObject(sql_article, new ArticleRowMapper(fields), id);
        List<Tag> tags = this.jdbcTemplate.query(sql_tags, new TagsExtractor(), id);

        article.setTagList(tags);
        return article;
    }

    @Override
    public ArticleDto update(Article article) {

        String sqlForArticles = "update articles set title = ?, content = ?, part_of_content = ? where id = ?";
        String sqlForDeleteArticlesTags = "delete from articles_tags where article_id = ?";

        int updateCount = this.jdbcTemplate.update(sqlForArticles, article.getTitle(), article.getContent(), article.getPartOfContent(), article.getId());
        if (updateCount != EXCPECTED_UPDATE_NUMBER) {
            // TODO: 実際に発行されるSQLをログに出力するようにする。
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sqlForArticles, EXCPECTED_UPDATE_NUMBER, updateCount);
        }
        this.jdbcTemplate.update(sqlForDeleteArticlesTags, article.getId());
        this.saveArticlesTags(article.getId(), article.getTagList());

        return this.selectForArticleDetail(article.getId(), null);
    }

    @Override
    public void delete(int id) {
        String sql = "delete from articles where id = ?";
        String sqlForArticlesTags = "delete from articles_tags where article_id = ?";
        this.jdbcTemplate.update(sql, id);
        this.jdbcTemplate.update(sqlForArticlesTags, id);
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
