//package mentorsjoy.api.repos;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.concurrent.TimeUnit;
//
//@Repository
//public class RegistrationRepositoryImpl implements RegistrationRepository {
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Override
//    public void setVerificationCode(String email) {
//        HashOperations<String, String, String> registration_info = redisTemplate.opsForHash();
//        Map<String, String> info = new HashMap<>();
//        info.put("code", Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000)));
//        info.put("attempts", "3");
//        registration_info.putAll(email, info);
//        redisTemplate.expire(email, 10, TimeUnit.MINUTES);
//    }
//
//    @Override
//    public String getVerificationCode(String email) {
//        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
//        return hashOps.get(email, "code");
//    }
//
//    @Override
//    public void decrementAttempts(String email) {
//        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
//        String attempts = hashOps.get(email, "attempts");
//        if (attempts != null) {
//            int newAttempts = Integer.parseInt(attempts) - 1;
//            hashOps.put(email, "attempts", String.valueOf(newAttempts));
//        }
//    }
//
//    @Override
//    public Long getExpirationTime(String email) {
//        return redisTemplate.getExpire(email, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Integer getAttempts(String email) {
//        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
//        return Integer.parseInt(Objects.requireNonNull(hashOps.get(email, "attempts")));
//    }
//}