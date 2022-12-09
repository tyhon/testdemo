package com.example.demo;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("blob")
public class BlobController {

//    @Value("azure-blob://sample-webapp/delegate.json")
//    private Resource blobFile;
//    String fileName = "delegate.json";
//    String container = "sample-webapp";

//    public void createBlobStorageClient(){
//        BlobServiceClient blobStorageClient = new BlobServiceClientBuilder()
//                .endpoint("https://secondtried.blob.core.windows.net/")
//                .credential(new DefaultAzureCredentialBuilder().build())
//                .buildClient();
//    }

    @GetMapping("/demo")
    public String getValue(){
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://secondtried.blob.core.windows.net/")
                .credential(new ManagedIdentityCredentialBuilder().build()) // Or use DefaultAzureCredentialBuilder
                .buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("sample-webapp");
        BlobClient blobClient = containerClient.getBlobClient("delegate.json");
        return blobClient.toString();
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDelegateList() throws IOException {
//        ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
//                .clientId("1ac684a9-00fa-4776-b124-28dcbdc3250c").build();
//        BlobServiceClient blobStorageClient = new BlobServiceClientBuilder()
//                .endpoint("https://secondtried.blob.core.windows.net/")
//                .credential(managedIdentityCredential)
//                .buildClient();
        BlobServiceClient blobStorageClient = new BlobServiceClientBuilder()
                .endpoint("https://secondtried.blob.core.windows.net")
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
        BlobClient blobClient = blobStorageClient.getBlobContainerClient("sample-webapp").getBlobClient("delegate.json");
        Resource blobFile = ((Resource) blobClient);

        return StreamUtils.copyToString(
                blobFile.getInputStream(),
                Charset.forName("UTF-8"));
    }

//    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String addDelegate(@RequestBody Delegate delegate) throws IOException {
//        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
//            String result = StreamUtils.copyToString(this.blobFile.getInputStream(), Charset.forName("UTF-8"));
//            ObjectMapper mapper = new ObjectMapper();
////            if (!result.isEmpty()){
//            List<Delegate> delegateList = new ArrayList<>();
//            delegateList = mapper.readValue(result, new TypeReference<List<Delegate>>() {});
//            List<Integer> allIds = delegateList.stream().map(item -> item.getId()).collect(Collectors.toList());
//            delegate.setId(allIds.get(allIds.size()-1)+1);
//            delegateList.add(delegate);
//            String jsonString = mapper.writeValueAsString(delegateList);
//            os.write(jsonString.getBytes());}
////        }
//        return "file was updated";
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String deleteDelegate(@PathVariable("id") Integer id) throws IOException{
//        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
//            String result = StreamUtils.copyToString(this.blobFile.getInputStream(), Charset.forName("UTF-8"));
//            ObjectMapper mapper = new ObjectMapper();
//            List<Delegate> delegateList = new ArrayList<>();
//            delegateList = mapper.readValue(result, new TypeReference<List<Delegate>>() {});
//            List<Integer> allIds = delegateList.stream().map(delegate -> delegate.getId()).collect(Collectors.toList());
//            if (allIds.contains(id)) {
//                delegateList.removeIf(delegate -> delegate.getId().equals(id));
//                String jsonString = mapper.writeValueAsString(delegateList);
//                os.write(jsonString.getBytes());
//                return "delete successfully";
//            } else {
//                String jsonString = mapper.writeValueAsString(delegateList);
//                os.write(jsonString.getBytes());
//                return "this id does not exist";
//            }
//        }
//    }
}
