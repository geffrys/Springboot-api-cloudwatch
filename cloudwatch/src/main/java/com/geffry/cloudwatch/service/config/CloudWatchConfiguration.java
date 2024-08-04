package com.geffry.cloudwatch.service.config;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Getter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class CloudWatchConfiguration {
    @Value("${aws.cloudwatch.logGroupName}")
    private String logGroupName;
    @Value("${aws.cloudwatch.logStreamName}")
    private String logStreamName;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.endpoint}")
    private String endpoint;

    @Bean
    @Profile("dev")
    public AWSLogs awsLogs() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        return AWSLogsClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
}
