package info.ryusukeblog.myblogapi.article.repository.extractor;

import info.ryusukeblog.myblogapi.article.dto.ArticleDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticlesExtractor implements ResultSetExtractor<List<ArticleDto>> {

    List<String> fields;

    public ArticlesExtractor(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public List<ArticleDto> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<ArticleDto> articles = new ArrayList<>();

        while (rs.next()) {
            ArticleDto articleDto = new ArticleDto();
            if (fields.contains("id")) {
                articleDto.setId(rs.getInt("id"));
            }
            if (fields.contains("title")) {
                articleDto.setTitle(rs.getString("title"));
            }
            if (fields.contains("content")) {
                articleDto.setContent(rs.getString("content"));
            }
            if (fields.contains("part_of_content")) {
                articleDto.setPartOfContent(rs.getString("part_of_content"));
            }
            if (fields.contains("is_writing")) {
                articleDto.setIsWriting(rs.getBoolean("is_writing"));
            }
            if (fields.contains("created_at")) {
                articleDto.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            }
            if (fields.contains("updated_at")) {
                articleDto.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
            }
            // TODO: tagListが無いので後で追加する
            articles.add(articleDto);
        }
        return articles;
    }
}
