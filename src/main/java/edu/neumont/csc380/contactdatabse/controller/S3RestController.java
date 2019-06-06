package edu.neumont.csc380.contactdatabse.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import edu.neumont.csc380.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        S3Object file = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
        byte[] bytes = IOUtils.toByteArray(file.getObjectContent());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Utils.getTypeFromFileName(fileName)))
                .body(bytes);
    }
}
