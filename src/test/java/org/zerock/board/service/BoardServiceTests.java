package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest // 스프링부트 어플리케이션 테스트에 필요한 거의 모든 의존성을 제공
public class BoardServiceTests {
    @Autowired // 객체의 의존성 자동 주입
    private BoardService boardService;
    
    @Test // 테스트를 수행하는 메소드, 각각의 테스트가 서로 영향을 주지 않고 독립적으로 실행됨
    public void testRegister() { // 게시판 글작성 테스트
        // BoardDTO 객체 생성
        BoardDTO dto = BoardDTO.builder()
                .title("test.")
                .content("Test...")
                .writerEmail("user1@zerock.org") // 현재 데이터베이스에 존재하는 회원 이메일
                .build();
        
        // 데이터베이스에 데이터 저장 후 게시글 번호 반환
        Long bno = boardService.register(dto);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet(){

        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    @Test
    public void testRemove(){

        Long bno = 35L;

        boardService.removeWithReplies(bno);

    }

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(3L)
                .title("제목 변경합니다.")
                .content("내용 변경!")
                .build();

        boardService.modify(boardDTO);
    }
}
