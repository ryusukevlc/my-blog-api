package info.ryusukeblog.myblogapi.tag;

import info.ryusukeblog.myblogapi.dto.TagDto;

import java.util.List;

public interface TagService {

    List<TagDto> findAll();

    TagDto save(TagDto tagDto);

    void delete(int id);
}
