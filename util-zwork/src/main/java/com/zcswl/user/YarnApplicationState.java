package com.zcswl.user;

/**
 * @author zhoucg
 * @date 2022-04-23 11:14
 */
public enum YarnApplicationState {

    NEW,
    NEW_SAVING,
    SUBMITTED,
    ACCEPTED,
    RUNNING,
    FINISHED,
    FAILED,
    KILLED;

    private YarnApplicationState() {
    }
}
