package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
@Log4j2
@RequiredArgsConstructor
public class SiteController {

    @GetMapping({"/workshop"})
    public void getWorkshop(){
        log.info("workshop");
    }

    @GetMapping({"/workshopW"})
    public void getWorkshopW(){
        log.info("workshopW");
    }

    @GetMapping({"/workshopS"})
    public void getWorkshopS(){
        log.info("workshopS");
    }

    @GetMapping({"/workshopM"})
    public void getWorkshopM(){
        log.info("workshopM");
    }

    @GetMapping({"/workshopC"})
    public void getWorkshopC(){
        log.info("workshopC");
    }

    @GetMapping({"/hob"})
    public void getHob(){
        log.info("hob");
    }

}
