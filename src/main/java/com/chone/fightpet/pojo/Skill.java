package com.chone.fightpet.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create 2021-02-18 11:51
 *
 * @author chone
 */
@NoArgsConstructor
@Data
public class Skill {
    /**
     * base_id : 2544
     * level : 5
     * progress : 21
     * cost : 2
     * current_desc : 生命总共增加12点
     * next_desc : 生命总共增加16点
     */

    private String base_id;
    private String level;
    private String progress;
    private String cost;
    private String current_desc;
    private String next_desc;
}
