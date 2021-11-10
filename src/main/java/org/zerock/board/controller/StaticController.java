package org.zerock.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/static")
@Log4j2
@RequiredArgsConstructor
public class StaticController {

    @GetMapping({"/index"})
    public void getIndex(){log.info("index");}
}
