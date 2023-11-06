package info.ryusukeblog.myblogapi.tag;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

    List<Tag> findAll();

    boolean save(Tag tag);

    void delete(int id);
}
