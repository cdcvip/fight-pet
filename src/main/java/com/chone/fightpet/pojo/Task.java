package com.chone.fightpet.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Create 2021-01-15 17:13
 *
 * @author chone
 */
@Setter
@Getter
@ToString
public class Task {
    //{"id":"107","title":"飘渺幻境","desc":"挑战一次飘渺幻境。","status":"4","gift":"奖励150经验"}
    /**
     * id
     */
    private String id;
    /**
     * 飘渺幻境
     */
    private String title;
    /**
     * 挑战一次飘渺幻境
     */
    private String desc;
    /**
     * status 状态：2待完成 3完成 4已领取
     */
    private String status;
    /**
     * 奖励150经验
     */
    private String gift;

}
