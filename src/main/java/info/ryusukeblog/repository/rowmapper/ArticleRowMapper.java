package info.ryusukeblog.repository.rowmapper;

import info.ryusukeblog.dto.ArticleDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleRowMapper implements RowMapper<ArticleDto> {

    List<String> fields;

    public ArticleRowMapper(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public ArticleDto mapRow(ResultSet rs, int rowNum) throws SQLException {

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

        if (fields.isEmpty()) {
            articleDto.setId(rs.getInt("id"));
            articleDto.setTitle(rs.getString("title"));
            articleDto.setContent(rs.getString("content"));
            articleDto.setPartOfContent(rs.getString("part_of_content"));
            articleDto.setIsWriting(rs.getBoolean("is_writing"));
            articleDto.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            articleDto.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        }

        return articleDto;
    }
}
