package com.geffry.cloudwatch.service.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AwsConfiguration {


    @Value("${aws.endpoint}")
    private String awsEndpoint;
    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    @Profile("dev")
    public AWSCredentials credentials() {
        return DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
    }
}
