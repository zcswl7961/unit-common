package com.consistency.raft;

import com.consistency.raft.executor.GlobalExecutor;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 */
public class RaftPeer {


    private String ip;

    private String voteFor;

    public AtomicLong term = new AtomicLong(0L);


    public State state = State.FOLLOWER;

    /**
     * 选主时间
     */
    public volatile long leaderDueMs = RandomUtils.nextLong(0, TimeUnit.SECONDS.toMillis(15L));

    /**
     * 心跳时间
     */
    public volatile long heartbeatDueMs = RandomUtils.nextLong(0, TimeUnit.SECONDS.toMillis(5L));


    public void resetLeaderDue() {
        leaderDueMs = GlobalExecutor.LEADER_TIMEOUT_MS + RandomUtils.nextLong(0, GlobalExecutor.RANDOM_MS);
    }

    public void resetHeartbeatDue() {
        heartbeatDueMs = GlobalExecutor.HEARTBEAT_INTERVAL_MS;
    }


    public enum State {
        /**
         * Leader of the cluster, only one leader stands in a cluster
         */
        LEADER,
        /**
         * Follower of the cluster, report to and copy from leader
         */
        FOLLOWER,
        /**
         * Candidate leader to be elected
         */
        CANDIDATE
    }

}
