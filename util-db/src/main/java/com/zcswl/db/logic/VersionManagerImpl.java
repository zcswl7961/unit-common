package com.zcswl.db.logic;


import com.zcswl.db.dao.VersionDao;
import com.zcswl.db.entity.Parameter;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * @author zhoucg
 * @date 2019-11-15 16:45
 */
public class VersionManagerImpl implements VersionManager{

    private VersionManagerNonSpringImpl _versionManager;

    private Exception dbScriptFileException;

    private Resource dbScriptIndex;

    public VersionManagerImpl(Parameter configParam, VersionDao versionDao, Resource dbScriptIndex) {
        this.dbScriptIndex = dbScriptIndex;
        File file = null;
        try {
            file = dbScriptIndex.getFile();
            new InputStreamReader(new FileInputStream(file));
        } catch (IOException e) {
            dbScriptFileException = e;
            return;
        }
        _versionManager = new VersionManagerNonSpringImpl(configParam, versionDao, file);
    }

    public VersionManagerImpl(Parameter configParam, VersionDao versionDao, Resource dbScriptIndex,
                              Resource mysqlScriptIndex) {
        this.dbScriptIndex = mysqlScriptIndex;
        File file;
        try {
            file = this.dbScriptIndex.getFile();
        } catch (IOException e) {
            dbScriptFileException = e;
            return;
        }
        _versionManager = new VersionManagerNonSpringImpl(configParam, versionDao, file);
    }

    public void init() throws Exception {
        smartUpdate();
    }




    @Override
    public String getVersionNum() {
        return _versionManager.getVersionNum();
    }

    @Override
    public Date getBuildDate() {
        return _versionManager.getBuildDate();
    }

    @Override
    public String getDBVersionNum() {
        return _versionManager.getDBVersionNum();
    }

    @Override
    public Date getDBBuildDate() {
        return _versionManager.getDBBuildDate();
    }

    @Override
    public void smartUpdate() throws Exception {
        if (dbScriptFileException != null)
            throw new RuntimeException("读取系统数据库升级的脚本文件[" + dbScriptIndex + " ]时出现错误,请检查该文件是否有效!\n", dbScriptFileException);
        _versionManager.smartUpdate();
    }

    @Override
    public boolean isNeedInit() {
        return _versionManager.isNeedInit();
    }

    @Override
    public boolean isNeedUpdate() {
        return _versionManager.isNeedUpdate();
    }
}
