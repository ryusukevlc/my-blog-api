package info.ryusukeblog.controller;

import info.ryusukeblog.dto.TagDto;
import info.ryusukeblog.dto.TagMapper;
import info.ryusukeblog.model.models.Tag;
import info.ryusukeblog.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    private TagService tagService;

    private TagMapper tagMapper;

    public TagController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return this.tagService.getTags();
    }

    @PostMapping("/tags")
    public Tag create(@RequestBody TagDto tagDto) {
        Tag tag = this.tagMapper.getTagFromDtoForCreate(tagDto);
        return this.tagService.save(tag);
    }
}
