package org.zerock.board.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.zerock.board.entity.ClubMember;
import org.zerock.board.entity.ClubMemberRole;
import org.zerock.board.repository.ClubMemberRepository;
import org.zerock.board.security.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service // 자동으로 스프링의 빈으로 등록
@RequiredArgsConstructor // 의존성 자동 주입, 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
public class ClubOauth2UserDetailService extends DefaultOAuth2UserService {

    private final ClubMemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("-------------------------------------------");
        log.info("userRequest: " + userRequest); // 파라미터로 넘어온 OAuth2UserRequest 객체

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: " + clientName); // Google로 출력
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("==========================================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k + ":" + v); // 구글 개발자 콘솔에서 추가한 범위 sub, picture, email, email_verified, Email 등이 출력
        });

        String email = null;
        if(clientName.equals("Google")) { // 구글을 이용하는 경우
            email = oAuth2User.getAttribute("email");
        }
        log.info("EMAIL: " + email);

//        ClubMember member = saveSocialMember(email); // 조금 뒤에 사용
//        return oAuth2User;

        ClubMember member = saveSocialMember(email);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(member.getEmail(), member.getPassword(), true,
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList()), oAuth2User.getAttributes());
                clubAuthMember.setName(member.getName());

                return clubAuthMember;
    }

    private ClubMember saveSocialMember(String email) {
        // 기존에 동일한 이메일로 가입한 회원이 있는 경우네는 그대로 조회만
        Optional<ClubMember> result = repository.findByEmail(email, true);

        if(result.isPresent()) {
            return result.get();
        }

        // 없다면 회원 추가 패스워드는 1111 이름은 그냥 이메일 주소로
        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        repository.save(clubMember);

        return clubMember;
    }
}
