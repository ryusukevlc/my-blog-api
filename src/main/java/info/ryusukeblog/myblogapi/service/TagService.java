package info.ryusukeblog.myblogapi.service;

import info.ryusukeblog.myblogapi.dto.TagDto;

import java.util.List;

public interface TagService {

    List<TagDto> findAll();

    TagDto save(TagDto tagDto);

    boolean delete(int id);
}
