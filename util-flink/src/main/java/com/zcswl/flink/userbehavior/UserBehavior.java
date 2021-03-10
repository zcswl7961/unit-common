package com.zcswl.flink.userbehavior;

/**
 * userId   itemId  categoryId behavior timestamp
 * 565218	2331370	3607361	pv	1511658003
 * @author zhoucg
 * @date 2021-03-09 18:37
 */
public class UserBehavior {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long itemId;
    /**
     * 类别id
     */
    private Integer categoryId;

    /**
     * 用户行为
     */
    private String behavior;

    /**
     * 对应的时间戳信息
     */
    private Long timestamp;

    public UserBehavior() {

    }

    public UserBehavior(Long userId, Long itemId, Integer categoryId, String behavior, Long timestamp) {
        this.userId = userId;
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.behavior = behavior;
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "UserBehavior{" +
                "userId=" + userId +
                ", itemId=" + itemId +
                ", categoryId=" + categoryId +
                ", behavior='" + behavior + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
