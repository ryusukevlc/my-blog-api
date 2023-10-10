package info.ryusukeblog.controller;

import info.ryusukeblog.dto.TagDto;
import info.ryusukeblog.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    private TagService tagService;


    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // @GetMapping("/tags")
    // public List<Tag> getTags() {
    //     return this.tagService.getTags();
    // }

    // @PostMapping("/tags")
    // public Tag create(@RequestBody TagDto tagDto) {
    //     Tag tag = this.tagMapper.getTagFromDtoForCreate(tagDto);
    //     return this.tagService.save(tag);
    // }
}
