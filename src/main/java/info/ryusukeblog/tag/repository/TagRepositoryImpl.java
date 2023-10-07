package info.ryusukeblog.tag.repository;

import info.ryusukeblog.tag.model.Tag;
import info.ryusukeblog.tag.repository.extractor.TagsExtractor;
import info.ryusukeblog.tag.repository.rowmapper.TagRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {


    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag save(Tag tag) {

        String sql = "insert into tags (name) values (?);";
        String sqlForGettingIdLatestTag = "select last_insert_id()";

        this.jdbcTemplate.update(sql, tag.getName());
        int id = this.jdbcTemplate.queryForObject(sqlForGettingIdLatestTag, Integer.class);

        return this.getTag(id);
    }

    @Override
    public List<Tag> getTags() {
        String sql = "select * from tags;";
        return this.jdbcTemplate.query(sql, new TagsExtractor());
    }

    @Override
    public Tag getTag(int id) {
        String sql = "select * from tags where id = ?;";
        return this.jdbcTemplate.queryForObject(sql, new TagRowMapper(), id);
    }
}
