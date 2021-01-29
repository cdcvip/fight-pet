package com.chone.fightpet;

import com.chone.fightpet.serivce.FightService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class FightPetApplication {

    public static void main(String[] args) {
//        SpringApplication.run(FightPetApplication.class, args);
        FightService.startUp();
    }

}
