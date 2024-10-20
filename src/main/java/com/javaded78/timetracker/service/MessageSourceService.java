package com.javaded78.timetracker.service;

public interface MessageSourceService {

    String generateMessage(String key);

    String generateMessage(String key, Object... args);
}
