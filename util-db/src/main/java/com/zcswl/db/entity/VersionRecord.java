package com.zcswl.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


/**
 * @author zhoucg
 * @date 2019-11-15 16:48
 */
@Data
@AllArgsConstructor
public class VersionRecord {

    private String id;

    private String versionNum;

    private Date buildDate;

    public VersionRecord() {}

}
