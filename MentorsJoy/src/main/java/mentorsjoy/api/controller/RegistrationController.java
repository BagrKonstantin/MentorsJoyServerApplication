//package mentorsjoy.api.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import mentorsjoy.api.service.EmailSenderService;
//import mentorsjoy.api.service.RegistrationService;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class RegistrationController {
//    @Autowired
//    private RegistrationService registrationService;
//    @Autowired
//    private EmailSenderService emailSenderService;
//
//    private void sendCode(String email) {
//        emailSenderService.sendVerificationCode(email, "Код подтверждения",
//                "Код подтверждения: " + registrationService.getVerificationCode(email) + "\nУ вас есть 10 минут на " +
//                        "активацию кода");
//    }
//
//    @PostMapping("/generate-code")
//    public ResponseEntity<Long> setVerificationCode(@RequestBody Map<String, String> registration) {
//        String email = registration.get("email");
//        if (registrationService.getVerificationCode(email) == null) {
//            registrationService.setVerificationCode(email);
//            //sendCode(email);
//        } else {
//            if (600L - registrationService.getExpirationTime(email) < 45L) {
//                return ResponseEntity.badRequest().body(45L - (600L - registrationService.getExpirationTime(email)));
//            } else {
//                registrationService.setVerificationCode(email);
//                //sendCode(email);
//            }
//        }
//        return ResponseEntity.ok().body(45L);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Long> getVerificationCode(@RequestBody Map<String, String> login) {
//        String email = login.get("email");
//        String code = login.get("code");
//        if (registrationService.getAttempts(email) == 0) {
//            if (600L - registrationService.getExpirationTime(email) < 45L) {
//                long secondsToRequest = 45L - (600L - registrationService.getExpirationTime(email));
//                return ResponseEntity.badRequest().body(secondsToRequest);
//            } else {
//                registrationService.setVerificationCode(email);
//                sendCode(email);
//                return ResponseEntity.ok().body(45L);
//            }
//        }
//        String storedCode = registrationService.getVerificationCode(email);
//        if (storedCode == null) {
//            return ResponseEntity.notFound().build();
//        }
//        if (!code.equals(storedCode)) {
//            registrationService.decrementAttempts(email);
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
//}