package info.ryusukeblog.tag.repository;

import info.ryusukeblog.tag.model.Tag;

import java.util.List;

public interface TagRepository {
    Tag save(Tag tag);

    List<Tag> getTags();

    Tag getTag(int id);
}
