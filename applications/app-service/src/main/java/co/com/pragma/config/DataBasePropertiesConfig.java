package co.com.pragma.config;

import co.com.bancolombia.secretsmanager.api.GenericManagerAsync;
import co.com.bancolombia.secretsmanager.api.exceptions.SecretException;
import co.com.pragma.dto.DataBaseSecretDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
public class DataBasePropertiesConfig {
    
    @Bean
    @Profile({"dev"})
    public AwsCredentialsProvider awsCredentialsProviderDev(GenericManagerAsync secretManager,
                                                            @Value("${aws.secrets.deployAws.name}") String deployAwsSecretName

    ) throws SecretException {
        DataBaseSecretDTO awsCredentialsDTO=secretManager.getSecret(deployAwsSecretName, DataBaseSecretDTO.class).block();
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                awsCredentialsDTO.accessKeyId(),
                awsCredentialsDTO.secretAccessKey()
        );
        return StaticCredentialsProvider.create(credentials);
    }
}
