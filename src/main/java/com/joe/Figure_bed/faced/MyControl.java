package com.joe.Figure_bed.faced;

import com.joe.Figure_bed.services.AliOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create by joe on 2019/6/12
 */
@Controller
public class MyControl {

    @Autowired
    private AliOssService ossService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping(value = "/imageUpload")
    @ResponseBody
    public String imageUpload(@RequestParam(value = "image") MultipartFile file) {
        try (
                InputStream inputStream = file.getInputStream()
        ) {
            ossService.putObject("", "", inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "进来了";
    }

}
