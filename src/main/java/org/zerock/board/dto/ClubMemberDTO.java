package org.zerock.board.dto;

import lombok.*;
import org.zerock.board.entity.ClubMemberRole;

import java.util.Set;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubMemberDTO {
    private String email; // 회원 이메일
    private String password; //회원 패스워드
    private String name; //회원 이름
    private boolean fromSocial; // 회원 소셜 로그인 여부
    private Set<ClubMemberRole> roleSet; // 회원 권한
}
