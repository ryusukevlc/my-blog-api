package info.ryusukeblog.myblogapi.tag;

import info.ryusukeblog.myblogapi.dto.TagDto;
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
    public void delete(int id) {
        this.tagMapper.delete(id);
    }

}
