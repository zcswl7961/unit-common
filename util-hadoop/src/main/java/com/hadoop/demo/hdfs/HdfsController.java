package com.hadoop.demo.hdfs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        boolean b = HdfsUtils.mkDirs("/hdfsxWL");
        HdfsUtils.create("/hdfsxWL/local.text", "周成功");
        return "zhoucg";
    }
}
