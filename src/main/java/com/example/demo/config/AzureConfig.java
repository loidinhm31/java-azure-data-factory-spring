package com.example.demo.config;

import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.datafactory.DataFactoryManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureConfig {
    @Value("${azure.clientId}")
    private String clientId;

    @Value("${azure.clientSecret}")
    private String clientSecret;

    @Value("${azure.tenantId}")
    private String tenantId;

    @Value("${azure.subscriptionId}")
    private String subscriptionId;

    @Bean
    public DataFactoryManager DataFactoryManager() {
        AzureProfile profile = new AzureProfile(tenantId, subscriptionId,AzureEnvironment.AZURE);
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
        return DataFactoryManager
                .authenticate(clientSecretCredential, profile);
    }
}
