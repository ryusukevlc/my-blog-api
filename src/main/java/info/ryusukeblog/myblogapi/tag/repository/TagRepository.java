package info.ryusukeblog.myblogapi.tag.repository;

import info.ryusukeblog.myblogapi.tag.model.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> getTags();
}
