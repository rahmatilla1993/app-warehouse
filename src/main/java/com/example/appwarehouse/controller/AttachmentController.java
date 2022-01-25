package com.example.appwarehouse.controller;

import com.example.appwarehouse.service.AttachmentService;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartHttpServletRequest request) throws IOException {
        return attachmentService.uploadFile(request);
    }

    @GetMapping("/download/{file_id}")
    public void downloadFileByFileId(@PathVariable Integer file_id, HttpServletResponse response) throws IOException {
        attachmentService.downloadFileByFileId(file_id, response);
    }
}
