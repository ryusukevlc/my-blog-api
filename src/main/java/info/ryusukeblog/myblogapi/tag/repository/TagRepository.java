package info.ryusukeblog.myblogapi.tag.repository;

import info.ryusukeblog.myblogapi.tag.model.Tag;

import java.util.List;

public interface TagRepository {
    Tag save(Tag tag);

    List<Tag> getTags();

    Tag getTag(int id);
}
