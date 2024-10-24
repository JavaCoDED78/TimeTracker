package com.javaded78.timetracker.config;

import com.javaded78.timetracker.repository.RecordRepository;
import com.javaded78.timetracker.repository.TaskRepository;
import com.javaded78.timetracker.repository.UserRepository;
import com.javaded78.timetracker.security.JwtTokenProvider;
import com.javaded78.timetracker.security.JwtUserDetailsService;
import com.javaded78.timetracker.security.props.JwtProperties;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.RecordService;
import com.javaded78.timetracker.service.UserService;
import com.javaded78.timetracker.service.impl.DefaultAuthService;
import com.javaded78.timetracker.service.impl.DefaultRecordService;
import com.javaded78.timetracker.service.impl.DefaultTaskService;
import com.javaded78.timetracker.service.impl.DefaultUserService;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "dmdqYmhqbmttYmNhamNjZWhxa25hd2puY2xhZWtic3ZlaGtzYmJ1dg=="
        );
        return jwtProperties;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository, MessageSourceService messageSourceService) {
        return new JwtUserDetailsService(userService(userRepository, messageSourceService));
    }

    @Bean
    public JwtTokenProvider tokenProvider(UserRepository userRepository, MessageSourceService messageSourceService) {
        return new JwtTokenProvider(jwtProperties(),
                userDetailsService(userRepository, messageSourceService),
                userService(userRepository, messageSourceService));
    }

    @Bean
    @Primary
    public DefaultUserService userService(UserRepository userRepository, MessageSourceService messageSourceService) {
        return new DefaultUserService(
                userRepository,
                testPasswordEncoder(),
                messageSourceService
        );
    }

    @Bean
    @Primary
    public DefaultTaskService taskService(TaskRepository taskRepository,
                                          MessageSourceService messageSourceService,
                                          RecordService recordService,
                                          DefaultUserService userService) {
        return new DefaultTaskService(taskRepository, messageSourceService, recordService, userService);
    }

    @Bean
    public DefaultRecordService recordService(RecordRepository recordRepository, MessageSourceService messageSourceService) {
        return new DefaultRecordService(recordRepository, messageSourceService);
    }

    @Bean
    @Primary
    public DefaultAuthService authService(UserRepository userRepository,
                                          AuthenticationManager authenticationManager,
                                          MessageSourceService messageSourceService) {
        return new DefaultAuthService(
                authenticationManager,
                userService(userRepository, messageSourceService),
                tokenProvider(userRepository, messageSourceService)
        );
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public TaskRepository taskRepository() {
        return Mockito.mock(TaskRepository.class);
    }

    @Bean
    public RecordRepository recordRepository() {
        return Mockito.mock(RecordRepository.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

    @Bean
    public MessageSourceService messageSourceService() {return Mockito.mock(MessageSourceService.class);
    }
}
