package info.ryusukeblog.myblogapi.tag.service;

import info.ryusukeblog.myblogapi.tag.model.Tag;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);

    List<Tag> getTags();
}
