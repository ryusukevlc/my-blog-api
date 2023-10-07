package info.ryusukeblog.service;

import info.ryusukeblog.model.models.Tag;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);

    List<Tag> getTags();
}
