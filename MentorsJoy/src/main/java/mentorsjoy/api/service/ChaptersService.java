package mentorsjoy.api.service;

import mentorsjoy.api.model.Chapters;
import mentorsjoy.api.repositories.ChaptersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaptersService {
    @Autowired
    private ChaptersRepository chaptersRepository;



    public List<Chapters> getAllChapters() {
        return chaptersRepository.findAll();
    }
}
