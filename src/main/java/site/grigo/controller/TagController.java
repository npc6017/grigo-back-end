package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.grigo.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/tag")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TagController {
    private final TagService tagService; //싱글톤이기 때문에 final이 필요함.

    @GetMapping("/setting")
    @ResponseBody
    public Map<String, List<String>> tagAll() {
        Map<String, List<String>> t = new HashMap<>();
        t.put("tags", tagService.getAllTagNames());
        return t;
    }

    //받은 tag 데이터를 헤더에 존재하는 userEmail을 통하여 tag 정보 추가 및 AccountTagRepo에 입력.
    @PostMapping("/setting")
    public void setTag(@RequestBody Map<String, List<String>> test, HttpServletRequest request, HttpServletResponse response) {
        List<String> tags = test.get("tags");
        for(String tag : tags)
            log.info("{}", tag);
        String token = request.getHeader("Authorization");
        tagService.saveTags(token, tags);
        response.setStatus(215);
    }
}