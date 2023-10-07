package info.ryusukeblog.repository;

import info.ryusukeblog.model.models.Tag;

import java.util.List;

public interface TagRepository {
    Tag save(Tag tag);

    List<Tag> getTags();

    Tag getTag(int id);
}
