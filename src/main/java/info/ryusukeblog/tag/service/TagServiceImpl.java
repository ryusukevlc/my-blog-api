package info.ryusukeblog.tag.service;

import info.ryusukeblog.tag.model.Tag;
import info.ryusukeblog.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag save(Tag tag) {
        return this.tagRepository.save(tag);
    }

    @Override
    public List<Tag> getTags() {
        return this.tagRepository.getTags();
    }

}
