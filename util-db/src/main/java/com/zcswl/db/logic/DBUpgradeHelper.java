package com.zcswl.db.logic;

import java.sql.Connection;

/**
 * @author zhoucg
 * @date 2019-11-15 17:41
 */
public interface DBUpgradeHelper {

    void setConnection(Connection conn);
}
