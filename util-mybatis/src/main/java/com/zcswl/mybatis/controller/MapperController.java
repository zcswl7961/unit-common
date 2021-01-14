package com.zcswl.mybatis.controller;

import com.zcswl.mybatis.entity.AnalysisSampleDO;
import com.zcswl.mybatis.entity.Author;
import com.zcswl.mybatis.entity.Select;
import com.zcswl.mybatis.mapper.AnalysisSampleMapper;
import com.zcswl.mybatis.mapper.AuthorMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author zhoucg
 * @date 2020-11-15 15:27
 */
@RestController
@RequestMapping("/mapper")
@AllArgsConstructor
public class MapperController {

    private final AnalysisSampleMapper analysisSampleMapper;



    @GetMapping("/{jobId}")
    public String mybatisTest(@PathVariable Long jobId) {


        List<Select<Long, Long>> maps = analysisSampleMapper.searchClassifyId(jobId);

        analysisSampleMapper.updateClassifId(maps);
//
//        // 设置一个轮询随机数
//        Random random = new Random();
//        int i = random.nextInt(1000000);
//        // 如果是奇数的话。插入第一个数据 ，如果是偶数的话，插入第二个数据
//        // 生成jobId
//        if (i % 2 == 0) {
////            List<Long> ids = new ArrayList<>();
////            for (int x = 0; x < 1000; x++) {
////                ids.add((long)random.nextInt(24228));
////            }
//            // 进行更新操作
//            //analysisSampleMapper.xx(ids);
//            //analysisSampleMapper.xx(ids);
//            int configId = random.nextInt(19);
//            analysisSampleMapper.yyy((long)jobId,(long)configId);
////            Long classifyId = random.nextLong();
////            analysisSampleMapper.updateBatch(classifyId, ids);
//        } else {
//            //analysisSampleMapper.saveClassifyId(jobId);
////            int configId = random.nextInt(19);
////            analysisSampleMapper.yyy((long)jobId,(long)configId);
//            int id = random.nextInt(66047);
//            analysisSampleMapper.zzz((long)id, (long)jobId);
//            //analysisSampleMapper.saveClassifyId();
//            analysisSampleMapper.ppp((long)jobId);
//        }
        return "SUCCESS";
    }
}
