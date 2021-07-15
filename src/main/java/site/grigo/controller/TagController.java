package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.tag.Tag;
import site.grigo.service.TagService;

import java.util.List;

@RequestMapping("/tag")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TagController {
    private final TagService tagService; //싱글톤이기 때문에 final이 필요함.

    @GetMapping("/setting")
    public List<Tag> tagAll() {
        tagService.test();
        return tagService.getAllTags();
    }

}