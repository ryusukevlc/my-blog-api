package info.ryusukeblog.myblogapi.tag.controller;

import info.ryusukeblog.myblogapi.tag.model.Tag;
import info.ryusukeblog.myblogapi.tag.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return this.tagService.getTags();
    }
}
