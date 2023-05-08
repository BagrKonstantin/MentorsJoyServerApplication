//package mentorsjoy.api.config;
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfiguration {
//
//    @Value("${spring.data.redis.host}")
//    private String host;
//
//    @Value("${spring.data.redis.port}")
//    private int port;
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(getJedisConnectionFactory());
//        StringRedisSerializer redisSerializer = new StringRedisSerializer();
//        template.setKeySerializer(redisSerializer);
//        template.setValueSerializer(redisSerializer);
//        template.setHashKeySerializer(redisSerializer);
//        template.setHashValueSerializer(redisSerializer);
//        return template;
//    }
//    @Bean
//    public JedisClientConfiguration getJedisClientConfiguration() {
//        final JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder =
//                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
//        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
//        return jedisPoolingClientConfigurationBuilder.poolConfig(genericObjectPoolConfig).build();
//    }
//    @Bean
//    public JedisConnectionFactory getJedisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        return new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
//    }
//}