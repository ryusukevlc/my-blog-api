package info.ryusukeblog.myblogapi.repository;

import info.ryusukeblog.myblogapi.model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

    List<Tag> findAll();

    boolean save(Tag tag);

    boolean delete(int id);
}
