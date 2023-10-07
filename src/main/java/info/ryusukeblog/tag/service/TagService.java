package info.ryusukeblog.tag.service;

import info.ryusukeblog.tag.model.Tag;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);

    List<Tag> getTags();
}
