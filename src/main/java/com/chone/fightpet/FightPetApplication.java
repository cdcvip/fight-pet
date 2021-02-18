package com.chone.fightpet;

import com.chone.fightpet.serivce.FightService;

//@SpringBootApplication
public class FightPetApplication {

    public static void main(String[] args) {
//        SpringApplication.run(FightPetApplication.class, args);
        FightService.startUp();
    }

}
