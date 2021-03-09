package com.chone.fightpet.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create 2021-03-04 14:28
 *
 * @author chone
 */
@lombok.NoArgsConstructor
@Setter
@Getter
public class Scene {

    /**
     * id : 1010
     * current_pos : 0
     * is_dead : 0
     * has_set_pal : 0
     * can_auto_fight : 1
     * can_be_resurrected : 0
     * min_level_player : {"uin":"1002697144","level":"41","ce":"15051","facname":"醉梦清歌","nickname":"　　"}
     * fastest_player : {"uin":"2202043570","level":"115","ce":"49586","facname":"破べ晓丶","nickname":"Azゞ 空城"}
     * npc : [{"pos":"1","npcid":"7101"},{"pos":"2","npcid":"7102"},{"pos":"3","npcid":"7103"},{"pos":"4","npcid":"7104"},{"pos":"5","npcid":"7105"},{"pos":"6","npcid":"7106"},{"pos":"7","npcid":"7107"},{"pos":"8","npcid":"7108"},{"pos":"9","npcid":"7109"},{"pos":"10","npcid":"7110"}]
     */

    private String id;
    private String current_pos;
    private String is_dead;
    private java.util.List<Npc> npc;

    public  int getNpcCount() {
        return npc.size();
    }

    @Setter
    @Getter
    static class Npc {
        private String pos;
        private String npcid;
    }


}
