package com.joe.picBed.faced;

import com.joe.picBed.entity.UploadRecordItem;
import com.joe.picBed.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Create by joe on 2019/6/12
 */
@Controller
public class MyControl {

    @Autowired
    private MyService myService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping(value = "/imageUpload")
    @ResponseBody
    public UploadRecordItem imageUpload(@RequestParam(value = "image") MultipartFile file) throws Exception {
        return myService.upload(file.getOriginalFilename(), file.getBytes());
    }

    @GetMapping(value = "/uploadRecords")
    @ResponseBody
    public List<UploadRecordItem> getUploadRecords() {
        return myService.getUploadRecords();
    }

}
