package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.ClubMember;
import org.zerock.board.entity.ClubMemberRole;
import org.zerock.board.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest // 스프링부트 어플리케이션 테스트에 필요한 거의 모든 의존성을 제공
public class MemberRepositoryTests { // 회원 테스트 클래스
    // DI(의존성 종속, Dependency Injection)란, 클래스간의 의존관계를 스프링 컨테이너가 자동으로 연결해주는 것
    // 해당 변수 및 메서드에 스프링이 관리하는 Bean을 자동으로 매핑
    @Autowired // 객체의 의존성 자동 주입
//    private MemberRepository memberRepository;
    private ClubMemberRepository clubMemberRepository;

    @Test // 테스트를 수행하는 메소드, 각각의 테스트가 서로 영향을 주지 않고 독립적으로 실행됨
    public void insertMembers() { // 회원 데이터 추가

        IntStream.rangeClosed(1, 3).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@aaa.com")
                    .password("1111") // 패스워드 암호화 추가해야 할 것
                    .name("USER" + i)
                    .fromSocial(false)
                    .build();

            // 기본 권한
            clubMember.addMemberRole(ClubMemberRole.USER);
            if(i > 1) clubMember.addMemberRole(ClubMemberRole.MANAGER);
            if(i > 2) clubMember.addMemberRole(ClubMemberRole.ADMIN);

            // 실제 mariaDB에 JPA를 이용해서 데이터 저장
            clubMemberRepository.save(clubMember);
        });
    }
}
