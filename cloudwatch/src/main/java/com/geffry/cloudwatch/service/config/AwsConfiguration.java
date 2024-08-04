package com.geffry.cloudwatch.service.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AwsConfiguration {


    @Bean
    @Profile("dev")
    public AWSCredentials credentials() {
        return DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
    }
}
