package com.chone.fightpet.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Create 2021-02-27 9:41
 *
 * @author chone
 */
@Getter
@Setter
public class Account {
    private String qq;
    private String uin;
    private String s_key;
    private String pt4_token;
    // 等级、活力、体力
    private String level = "59";
    private int energy = 10;
    private int vigor = 10;
    // 好友列表、帮派成员、斗友列表
    private List<Object> member, friends, stranger;
    // 好友_NPC and 帮派_NPC
    private Map<String,String> friends_npc;
}
