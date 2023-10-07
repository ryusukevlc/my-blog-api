package info.ryusukeblog.tag.repository.extractor;

import info.ryusukeblog.tag.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TagsExtractor implements ResultSetExtractor<List<Tag>> {


    @Override
    public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<Tag> tags = new ArrayList<>();

        while (rs.next()) {
            Tag tag = new Tag(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getObject("updated_at", LocalDateTime.class)
            );
            tags.add(tag);
        }
        return tags;
    }

}
