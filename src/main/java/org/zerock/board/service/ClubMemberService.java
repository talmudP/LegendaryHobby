package org.zerock.board.service;

import org.zerock.board.dto.ClubMemberDTO;
import org.zerock.board.entity.ClubMember;
import org.zerock.board.entity.ClubMemberRole;

import java.util.Set;

public interface ClubMemberService {

    void register(ClubMemberDTO dto);

    default ClubMember dtoToEntity(ClubMemberDTO dto) {
        ClubMember clubMember = ClubMember.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .fromSocial(false)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        return clubMember;
    }

    default ClubMemberDTO entityToDTO(ClubMember clubMember) {
        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
                .email(clubMember.getEmail())
                .password(clubMember.getPassword())
                .name(clubMember.getName())
                .fromSocial(clubMember.isFromSocial())
                .roleSet(clubMember.getRoleSet())
                .build();
        return clubMemberDTO;
    }

}
