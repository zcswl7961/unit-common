package com.hadoop.demo.hdfs;


import java.util.List;

/**
 * a simple test for hdfs
 * <p>
 * <p>
 * Unable to load native-hadoop library for your platform... using builtin-java classes where applicable 问题
 *
 * @author zhoucg
 * @date 2021-02-19 15:13
 */
public class Test {


    public static void main(String[] args) throws Exception {
        HdfsUtils.setUp();

        // 查询对应的hdfs的文件
        List<HDFSResource> hdfsResources = HdfsUtils.listFiles("/");
        for (HDFSResource hdfsResource : hdfsResources) {
            System.out.println(hdfsResource.toString());
        }

        // 创建一个hdfs的文件
        boolean b = HdfsUtils.mkDirs("/hdfsZzz");
        // HdfsUtils.create("/hdfsZzz/local.text", "周成功");

        // 获取hdfs文件
        HdfsUtils.cat("/hdfsZzz/local.text");

    }
}
