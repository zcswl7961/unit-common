package com.zcswl.db;

import com.zcswl.db.configruation.DbConfiguration;
import com.zcswl.db.dao.VersionDao;
import com.zcswl.db.entity.Parameter;
import com.zcswl.db.logic.VersionManagerImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 线上版本发布，获取对应的数据库连接，并增量初始化执行数据库的系统数据
 * <p>在一般的Pass平台构建中，我们需要将执行的发布包（例如tar.gz）上传并一键部署，因此需要构建初始化的数据库，并在后期的增量中
 *    增量修改执行的版本数据
 *
 * @author zhoucg
 * @date 2019-11-15 15:46
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DBInit {

    private final DbConfiguration dbConfiguration;

    private final VersionDao versionDao;

    /**
     * 在这里，我们定义执行的schema.sql中的引擎规则为对应的 -- version x.x.x,build yyyy-mm-dd 规则
     * 其中 version 后面接着的是对应的版本号数据
     * yyyy-mm-dd 为当前系统版本发布的时间
     */
    private static final String PATTERN_STR = "^--version [0-9]+[.][0-9]+[.][0-9]+,build [0-9]{4}-[0-9]{1,2}-[0-9]{1,2}.*";

    private static final Pattern READLINE = Pattern.compile(PATTERN_STR);

    /**
     * 通过符合规则的匹配版本信息找到当前发布的版本号，以及对应的发布时间
     */
    private static final String VERSION_DATE_STR = "[0-9]+[.-][0-9]+[.-][0-9]+";

    private static final Pattern PATTERN_VERSION_DATE = Pattern.compile(VERSION_DATE_STR);

    /**
     * 编码类型
     */
    private static final String CHARSET = "UTF-8";

    @PostConstruct
    public void init() {

        log.info("check whether the database is initialized and upgraded......");
        File databaseFile = getFile();
        try{
            List<String> readLine = FileUtils.readLines(databaseFile,CHARSET);
            //匹配执行的规则数据
            String lastVersionLine = null;
            for(String line : readLine) {
                if(line != null && READLINE.matcher(line).matches()) {
                    lastVersionLine = line;
                }
            }
            if(lastVersionLine == null) {
                throw new RuntimeException("database init error，database_init.sql version Format exception");
            }
            Matcher matcher = PATTERN_VERSION_DATE.matcher(lastVersionLine);
            matcher.find();
            String versionNumberStr = matcher.group();
            matcher.find();
            String buildDate = matcher.group();
            Parameter parameter = new Parameter(dbConfiguration.getSchema(),versionNumberStr,buildDate,CHARSET);
            Resource sqlResource = new FileSystemResource(databaseFile);
            VersionManagerImpl versionManager = new VersionManagerImpl(parameter,versionDao,sqlResource);
            versionManager.init();

        } catch (Exception e) {
            log.error("database init error:{}",e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 加载当前系统中的数据库文件
     * @return
     */
    private File getFile() {

        URL url = DBInit.class.getClassLoader().getResource("database/schema.sql");
        File file = new File(url.getFile());
        log.info("first,get the database/schema.sql，file:{}",file);
        if(file.exists()) {
            return file;
        }
        url = DBInit.class.getClassLoader().getResource("schema.sql");
        file = new File(url.getFile());
        log.info("now we get schema.sql,file:{}",file);
        if(file.exists()) {
            return file;
        }
        throw new RuntimeException("\t\n" +
                "The schema.sql file under the execution directory is not present. Please check the system configuration");
    }

}
