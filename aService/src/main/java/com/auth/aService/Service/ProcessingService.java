package com.auth.aService.Service;

import com.auth.aService.Model.Log;
import com.auth.aService.Model.User;
import com.auth.aService.Repository.LogRepository;
import com.auth.aService.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessingService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final String serviceBUrl;
    private final String internalToken;

    public ProcessingService(
            LogRepository logRepository,
            UserRepository userRepository,
            @Value("${serviceB.url:http://data-api:8081/api/transform}") String serviceBUrl,
            @Value("${internal.token}") String internalToken
    ) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.serviceBUrl = serviceBUrl;
        this.internalToken = internalToken;
        this.restTemplate = new RestTemplate();
    }

    @Transactional
    public String process(UUID userId, String text) {
        // 1) Вызов сервиса B
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Token", internalToken);

        Map<String, String> requestBody = Map.of("text", text);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(serviceBUrl, httpEntity, Map.class);
        if (response == null || !response.containsKey("result")) {
            throw new IllegalStateException("Invalid response from Service B");
        }
        String result = String.valueOf(response.get("result"));

        // 2) Сохранение лога в БД
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Log log = new Log();
        log.setUser(user);
        log.setInput_text(text);
        log.setOutput_text(result);
        log.setCreated_at(OffsetDateTime.now());
        logRepository.save(log);

        // 3) Возврат результата
        return result;
    }
}