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
     * 斗友召回
     * canbecall":[{"level":"38","uin":"QQ","name":"昵称"}
     */
    private List<Uin> canbecall;

}
