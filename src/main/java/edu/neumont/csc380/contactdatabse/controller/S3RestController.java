package edu.neumont.csc380.contactdatabse.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.mchange.io.FileUtils;
import edu.neumont.csc380.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("media")
public class S3RestController
{
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${cloud.aws.bucket}")
    private String bucket;

    @GetMapping
    public void getAllFileFromS3()
    {
        ListObjectsRequest listBucketsRequest = new ListObjectsRequest().withBucketName(bucket);
        ObjectListing objectListing = amazonS3Client.listObjects(listBucketsRequest);

        List<S3ObjectSummary> s3ObjectSummary = objectListing.getObjectSummaries();

        //TODO figure out how to return mulitple file
    }

    @GetMapping("{username}/{fileName}")
    public ResponseEntity<byte[]> getFileFromS3(@PathVariable String username, @PathVariable String fileName) throws IOException
    {
        S3Object file = amazonS3Client.getObject(new GetObjectRequest(bucket, username + "/" + fileName));
        byte[] bytes = IOUtils.toByteArray(file.getObjectContent());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Utils.getTypeFromFileName(fileName)))
                .body(bytes);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public void uploadFiletoS3(
            @RequestParam("file")
                    MultipartFile file,
                    Principal p) throws IOException
    {
        File f = null;
        file.transferTo(f);
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, p.getName() + "/" + file.getName(), f));
    }
}
