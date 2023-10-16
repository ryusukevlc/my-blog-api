package info.ryusukeblog.myblogapi.service.impl;

import info.ryusukeblog.myblogapi.dto.TagDto;
import info.ryusukeblog.myblogapi.model.Tag;
import info.ryusukeblog.myblogapi.repository.TagMapper;
import info.ryusukeblog.myblogapi.service.TagService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    TagMapper tagMapper;
    ModelMapper modelMapper;

    public TagServiceImpl(TagMapper tagMapper, ModelMapper modelMapper) {
        this.tagMapper = tagMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDto> findAll() {
        return this.modelMapper.map(this.tagMapper.findAll(), new TypeToken<List<TagDto>>() {
        }.getType());
    }

    @Override
    public TagDto save(TagDto tagDto) {
        this.tagMapper.save(this.modelMapper.map(tagDto, Tag.class));
        return tagDto;
    }

    @Override
    public boolean delete(int id) {
        return this.tagMapper.delete(id);
    }

}
