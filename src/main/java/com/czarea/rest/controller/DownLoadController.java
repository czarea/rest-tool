package com.czarea.rest.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhouzx
 */
@Controller
public class DownLoadController {

    @GetMapping("/foos/download")
    public ResponseEntity download() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=test.txt");
        return new ResponseEntity(new ClassPathResource("test.txt"), headers, HttpStatus.OK);
    }
}
