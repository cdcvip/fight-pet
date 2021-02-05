package com.chone.fightpet.pojo;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Create 2021-02-05 9:54
 *
 * @author chone
 */
@NoArgsConstructor
@Setter
public class WzRyData {

    /**
     * code : 100000
     * msg : 物品已经发到您游戏账号！
     * GTips :
     * id : 15747
     * GName : 王者荣耀通行证公众号专属每日礼包
     * GIntro : 1胜经验卡x1 小喇叭x1
     */

    private Integer code;
    private String msg;
    //    private Integer id;
    private String GName;
    private String GIntro;

    /**
     * code : 100000
     */
    public Integer getCode() {
        return code;
    }

    /**
     * msg : 物品已经发到您游戏账号！
     */
    public String getMsg() {
        return msg;
    }

    /**
     * GName : 王者荣耀通行证公众号专属每日礼包
     */
    public String getGName() {
        return GName;
    }

    /**
     * GIntro : 1胜经验卡x1 小喇叭x1
     */
    public String getGIntro() {
        return GIntro;


    }


}
