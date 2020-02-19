package com.joe.picBed.faced;

import com.joe.picBed.entity.UploadRecordItem;
import com.joe.picBed.services.LoginService;
import com.joe.picBed.services.UploadService;
import com.joe.picBed.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Create by joe on 2019/6/12
 */
@Controller
public class MyControl {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        if (loginService.hasLogin(request)) {
            return "index.html";
        }
        return "login.html";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(String user, String pw) throws NoSuchAlgorithmException {
        if (loginService.verifyLogin(user, pw)) {
            return Tools.md5(user + pw);
        }
        return "";
    }


    @PostMapping(value = "/imageUpload")
    @ResponseBody
    public UploadRecordItem imageUpload(@RequestParam(value = "image") MultipartFile file, HttpServletRequest request) throws Exception {
        if (loginService.hasLogin(request)) {
            return uploadService.upload(file.getOriginalFilename(), file.getBytes());
        }
        return null;
    }

    @GetMapping(value = "/uploadRecords")
    @ResponseBody
    public List<UploadRecordItem> getUploadRecords(HttpServletRequest request) {
        if (loginService.hasLogin(request)) {
            return uploadService.getUploadRecords();
        }
        return null;
    }

}
