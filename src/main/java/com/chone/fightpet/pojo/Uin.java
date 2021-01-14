package com.chone.fightpet.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Create 2021/1/7 16:38
 *
 * @author chone
 */
@Setter
@Getter
public class Uin {
    /**
     * 好友召回[等级]
     */
    private String level;
    /**
     * QQ号
     */
    private String uin;
    private String name;
    /**
     * 好友列表[等级]
     */
    private String lilian;

}
