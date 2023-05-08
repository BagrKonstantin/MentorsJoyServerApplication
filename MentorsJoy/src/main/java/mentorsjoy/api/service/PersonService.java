package mentorsjoy.api.service;

import mentorsjoy.api.model.Person;
import mentorsjoy.api.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public void save(Person person) {
        personRepository.save(person);
    }
}
