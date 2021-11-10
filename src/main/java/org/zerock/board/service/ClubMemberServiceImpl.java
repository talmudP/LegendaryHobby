package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.ClubMemberDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.ClubMember;
import org.zerock.board.repository.ClubMemberRepository;

@Service
@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성, 의존성 주입(Dependency Injection) 편의성을 위해서 사용
@Log4j2
public class ClubMemberServiceImpl implements ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(ClubMemberDTO dto) {
        // Entity 객체로 변환 전 로그 출력
        log.info(dto);

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Entity 객체로 변환 후 Board Entity 클래스 타입의 변수에 대입
        ClubMember clubMember = dtoToEntity(dto);
        // JPA를 이용하여 데이터베이스에 board 객체의 데이터를 저장(insert)
        clubMemberRepository.save(clubMember);
    }
}
