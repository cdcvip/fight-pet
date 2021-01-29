package com.chone.fightpet.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create 2021-01-05 14:52
 *
 * @author chone
 */
@Getter
@Setter
public class ResultMsg {
    /**
     * 结果code
     * "result":"-1"
     */
    private String result;
    /**
     * "msg":"礼包已经领取过了！"
     */
    private String msg;
    /**
     * 历练-获取佣兵
     * "drop": "恭喜你抢到了赵敏碎片*1",
     */
    private String drop;

    /**
     * 每日任务-领取详情
     */
    private String award;
    /**
     *
     */
    private List<Task> tasklist;

    /********
     * 斗友召回
     * canbecall":[{"level":"38","uin":"QQ","name":"昵称"}
     */
    private List<Uin> canbecall;

    /**
     * 获取好友列表
     */
    private List<Uin> info;
    /**
     * 获取帮友列表
     */
    private List<Uin> list;

    /*********
     *  逗乐达人信息
     *  {"result":"0","msg":"","lvl":"7","score":"13555","speed":"15","day":"102"..}
     * 当前等级
     */
    private String lvl;
    /**
     * 达人积分
     */
    private String score;
    /**
     * 成长速度
     */
    private String speed;
    /**
     * 剩余天数
     */
    private String day;

    /**
     * 画卷迷踪 id
     * ,"repid":"1282722653_7_6920168529858265728",
     */
    private String repid;

    /**
     * 镖行天下-镖师-id[0-3:越大越好]
     */
    private String npc_id;
    /**
     *
     */
    private String looted_count;

}
