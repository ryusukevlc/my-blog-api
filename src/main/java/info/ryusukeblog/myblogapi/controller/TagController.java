package info.ryusukeblog.myblogapi.controller;

import info.ryusukeblog.myblogapi.dto.TagDto;
import info.ryusukeblog.myblogapi.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TagController {

    private TagService tagService;


    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<TagDto> findAll() {
        return this.tagService.findAll();
    }

    @PostMapping("/tags")
    public TagDto save(@RequestBody TagDto tagDto) {
        return this.tagService.save(tagDto);
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        boolean hasDeleted = this.tagService.delete(id);
        if (hasDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
