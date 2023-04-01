package info.ryusukeblog.myblogapi.tag.service;

import info.ryusukeblog.myblogapi.tag.model.Tag;
import info.ryusukeblog.myblogapi.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getTags() {
        return this.tagRepository.getTags();
    }

}
