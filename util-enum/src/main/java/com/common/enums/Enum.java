package com.common.enums;

/**
 * 枚举类
 * @author: zhoucg
 * @date: 2019-09-19
 */
public enum Enum {

    /**
     * 春天数据
     */
    SPRING("01","1121","春天"),

    /**
     * 夏天数据
     */
    SUMMMER("02","1130","夏天"),

    /**
     * 秋天数据
     */
    FAIL("03","1140","秋天"),

    /**
     * 冬天数据
     */
    WINTER("04","1150","冬天");


    /**
     * 编码数据
     */
    private String code;


    /**
     * 得分数
     */
    private String score;

    /**
     * 描述数据
     */
    private String description;


    Enum(String code,String score,String description) {
        this.code = code;
        this.score = score;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }

    public static String getScore(String code) {
        for(Enum value : Enum.values()) {
            if(value.getCode().equals(code)) {
                return value.getScore();
            }
        }
        return null;
    }
}
