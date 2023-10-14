package info.ryusukeblog.myblogapi.controller;

import info.ryusukeblog.myblogapi.service.TagService;
import org.springframework.web.bind.annotation.RestController;

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
