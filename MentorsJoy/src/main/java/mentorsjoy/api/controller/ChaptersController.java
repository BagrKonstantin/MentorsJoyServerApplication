package mentorsjoy.api.controller;

import mentorsjoy.api.model.Chapters;
import mentorsjoy.api.service.ChaptersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/extra")
public class ChaptersController {

    @Autowired
    private ChaptersService chaptersService;

    @GetMapping(path="/get-all-chapters")
    public List<Chapters> getAllChapters() {
        return chaptersService.getAllChapters();
    }
}
