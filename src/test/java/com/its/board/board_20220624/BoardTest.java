package com.its.board.board_20220624;

import com.its.board.board_20220624.common.PagingConst;
import com.its.board.board_20220624.dto.BoardDTO;
import com.its.board.board_20220624.dto.MemberDTO;
import com.its.board.board_20220624.entity.BoardEntity;
import com.its.board.board_20220624.entity.MemberEntity;
import com.its.board.board_20220624.repository.BoardRepository;
import com.its.board.board_20220624.repository.MemberRepository;
import com.its.board.board_20220624.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private BoardService boardService;

    public BoardDTO newBoard(int i) {
        BoardDTO board =
                new BoardDTO("title"+i, "writer"+i, "password"+i, "contents"+i);
        return board;
    }

    @Test
    @Transactional
    @Rollback
    public void newMember(){
        MemberDTO memberDTO = new MemberDTO("email","pw1","name1");
        memberRepository.save(MemberEntity.tosaveEntity(memberDTO));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 테스트")
    public void memberBoardFindByIdTest(){
        //위에서 저장한 테이블 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(1L);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            System.out.println("boardEntity.getId() = " + boardEntity.getId());
            //select * from board_table where board_id = 1
            System.out.println("boardEntity.getBoardTitle() = " + boardEntity.getBoardTitle());
            //게시글 작성자의 이름을 보고싶다면? 조인쿼리 사용했었음 select m.member_name from member_table m, board_table b where m.member_id = b.member_id where b.memberId =1
            //객체 그래프 탐색
            System.out.println("boardEntity.getMemberEntity().getMemberName() = " + boardEntity.getMemberEntity().getMemberName());
        }
    }





//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void saveTest() {
//        IntStream.rangeClosed(1,20).forEach(i -> {
//            boardService.save(newBoard(i));
//        });
//    }
    
    @Test
    public void PagingTest() {
        int page = 5;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부

        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                ));

        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부
    }

}
