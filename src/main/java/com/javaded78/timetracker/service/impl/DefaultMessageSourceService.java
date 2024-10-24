package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.service.MessageSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMessageSourceService implements MessageSourceService {

    private final Environment environment;

    @Override
    public String generateMessage(String messageKey) {
        return Optional.ofNullable(environment.getProperty(messageKey))
                .orElseThrow(() -> new RuntimeException("Property '%s' is missing".formatted(messageKey)));
    }

    @Override
    public String generateMessage(String messageKey, Object... messageArgs) {
        String messageFormat = generateMessage(messageKey);
        return String.format(messageFormat, messageArgs);
    }
}
