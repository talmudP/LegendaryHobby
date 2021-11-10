package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.board.dto.ClubMemberDTO;
import org.zerock.board.security.dto.ClubAuthMemberDTO;
import org.zerock.board.service.ClubMemberService;

@Controller
@Log4j2
@RequestMapping("/account")
@RequiredArgsConstructor
public class SampleController {

    private final ClubMemberService clubMemberService;

    @GetMapping("/register")
    public void exRegister() {
        log.info("exRegister..........");
    }

    @GetMapping("/success")
    public void exsuccess() {
        log.info("success..........");
    }

    @PostMapping("/success")
    public void exsuccessPost(ClubMemberDTO dto) {
        log.info("successPost..........");
        clubMemberService.register(dto);
        log.info("dto..." + dto);
    }

    @GetMapping("/login")
    public void exLogin() {
        log.info("exLogin..........");
    }

}
