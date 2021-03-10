package com.zcswl.flink.userbehavior;

/**
 * @author zhoucg
 * @date 2021-03-09 18:54
 */
public class ItemViewCount {

    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 分析结束时间
     */
    private Long windowEnd;

    /**
     * 商品总数
     */
    private Long count;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(Long windowEnd) {
        this.windowEnd = windowEnd;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ItemViewCount(Long itemId, Long windowEnd, Long count) {
        this.itemId = itemId;
        this.windowEnd = windowEnd;
        this.count = count;
    }

    public ItemViewCount() {

    }

    @Override
    public String toString() {
        return "ItemViewCount{" +
                "itemId=" + itemId +
                ", windowEnd=" + windowEnd +
                ", count=" + count +
                '}';
    }
}
