//package mentorsjoy.api.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import mentorsjoy.api.repos.RegistrationRepository;
//
//@Service
//public class RegistrationService {
//    @Autowired
//    private RegistrationRepository registrationRepository;
//    public void setVerificationCode(String email) {
//        registrationRepository.setVerificationCode(email);
//    }
//    public String getVerificationCode(String email) {
//        return registrationRepository.getVerificationCode(email);
//    }
//    public void decrementAttempts(String email) {
//        registrationRepository.decrementAttempts(email);
//    }
//    public Long getExpirationTime(String email) {
//        return registrationRepository.getExpirationTime(email);
//    }
//    public Integer getAttempts(String email) {
//        return registrationRepository.getAttempts(email);
//    }
//}