package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.grigo.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/tag")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TagController {
    private final TagService tagService; //싱글톤이기 때문에 final이 필요함.

    @GetMapping("/setting")
    public List<String> tagAll() {
        return tagService.getAllTagNames();
    }

    @PostMapping("/setting")
    public void setTag(@RequestBody List<String> tags, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        tagService.saveTags(token, tags);
        response.setStatus(215);
    }
}