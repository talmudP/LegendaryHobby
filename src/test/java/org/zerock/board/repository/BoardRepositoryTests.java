package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.ClubMember;
import org.zerock.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 10).forEach(i -> { // 게시판 데이터 저장 테스트
//            Member member = Member.builder().email("user" + i + "@aaa.com").build();
            ClubMember clubMember = ClubMember.builder().email("admin" + i + "@test.com").build();

            Board board = Board.builder()
                    .title("Title..." + i) // 글 제목
                    .content("Content..." + i) // 글 내용
                    .email(clubMember) // 글 작성자(ID)
                    .build();

            boardRepository.save(board);
        });
    }

    // 지연 로딩으로 처리하기 때문에, board.getWriter() 호출 시, member 테이블과 연결이 이미 끊어진 상태
    @Transactional // 해당 메서드를 하나의 트랜잭션으로 처리하라는 의미, 와 no Session 발생 시 데이터베이스연결 생성
    @Test
    public void testRead1() { // 게시판 조회 테스트
        // Optional<Entity>: Null 처리를 돕는 Optional 클래스, NullPointerException 예외 처리
        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에 존재하는 번호
        // get(): board 테이블에서 bno가 100인 데이터를 가져옴
        Board board = result.get();

        // board 테이블에서 bno가 100인 데이터 출력
        System.out.println(board);
        // board 테이블에서 bno가 100인 데이터의 writer_email과 같은 데이터를 member 테이블에서 출력
        System.out.println(board.getEmail());
    }

    @Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3() {
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending().and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }
}
