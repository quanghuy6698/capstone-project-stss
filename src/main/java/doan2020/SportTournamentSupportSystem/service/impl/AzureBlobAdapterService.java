package doan2020.SportTournamentSupportSystem.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

import doan2020.SportTournamentSupportSystem.config.Const;

@Component
public class AzureBlobAdapterService {

    private static final Logger logger = LoggerFactory.getLogger(AzureBlobAdapterService.class);

    @Autowired
    private CloudBlobClient cloudBlobClient;

    public void createContainer(String containerName){
        CloudBlobContainer container = null;
        try {
            container = cloudBlobClient.getContainerReference(containerName);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (StorageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        try {
        	System.out.println(containerName);
        	System.out.println(container);
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (StorageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public URI upload(MultipartFile multipartFile, String containerName, String type){
        URI uri = null;
        CloudBlockBlob blob = null;
        String fileName = null;
        try {
        	CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
        	
        	String OriginalFilename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        	
        	if(type==Const.AVATAR) {
				fileName = (FilenameUtils.getBaseName(Const.AVATAR) + "-" +OriginalFilename);
			}else if(type == Const.BACKGROUND){
				fileName = (FilenameUtils.getBaseName(Const.BACKGROUND) + "-" +OriginalFilename);
			}
     
            blob = container.getBlockBlobReference(fileName);
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    public List<URI> listBlobs(String containerName){
        List<URI> uris = new ArrayList<>();
        try {
            CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
            for (ListBlobItem blobItem : container.listBlobs()) {
                uris.add(blobItem.getUri());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return uris;
    }

    public void deleteBlob(String containerName, String blobName){
        try {
            CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
            CloudBlockBlob blobToBeDeleted = container.getBlockBlobReference(blobName);
            blobToBeDeleted.deleteIfExists();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }
}
