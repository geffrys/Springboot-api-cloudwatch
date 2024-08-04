package com.geffry.cloudwatch.service.config;


import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Getter
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class CloudWatchConfiguration {
    @Value("${aws.cloudwatch.logGroupName}")
    private String logGroupName;
    @Value("${aws.cloudwatch.logStreamName}")
    private String logStreamName;

    @Bean
    @Profile("dev")
    public AWSLogs awsLogs() {
        return AWSLogsClientBuilder.standard()
                .build();
    }
}
