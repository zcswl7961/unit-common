package com.hadoop.demo.hdfs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhoucg
 * @date 2021-02-19 16:39
 */
@RestController
@RequestMapping("/hdfs")
public class HdfsController {


    @GetMapping
    public String createHdfs() throws Exception {
        HdfsUtils.setUp();
        List<HDFSResource> hdfsResources = HdfsUtils.listFiles("/");
        for (HDFSResource hdfsResource : hdfsResources) {
            System.out.println(hdfsResource.toString());
        }
        return "zhoucg";
    }
}
