package doan2020.SportTournamentSupportSystem.config;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;

@Configuration
public class BeanConfig {

    @Bean
    public CloudBlobClient cloudBlobClient() throws URISyntaxException, StorageException, InvalidKeyException {
    	CloudStorageAccount storageAccount = CloudStorageAccount.parse(Const.CONNECTION_STRING);
        return storageAccount.createCloudBlobClient();
    }
}
