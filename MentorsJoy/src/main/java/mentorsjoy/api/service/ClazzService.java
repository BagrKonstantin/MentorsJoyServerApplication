package mentorsjoy.api.service;

import mentorsjoy.api.model.Classes;
import mentorsjoy.api.repositories.ClazzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClazzService {
    @Autowired
    private ClazzRepository clazzRepository;

    public Classes findById(Integer clazzId){
        return clazzRepository.findByClassId(clazzId).get();
    }
}
