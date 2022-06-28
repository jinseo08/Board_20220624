package com.its.board.board_20220624.service;

import com.its.board.board_20220624.common.PagingConst;
import com.its.board.board_20220624.dto.BoardDTO;
import com.its.board.board_20220624.entity.BoardEntity;
import com.its.board.board_20220624.entity.MemberEntity;
import com.its.board.board_20220624.repository.BoardRepository;
import com.its.board.board_20220624.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        MultipartFile boardFile = boardDTO.getBoardFile();
        String boardFilename= boardFile.getOriginalFilename();
        boardFilename = System.currentTimeMillis() + "_" + boardFilename;
        String savePath = "C:\\springboot_img\\" + boardFilename;
        if(!boardFile.isEmpty()){
            boardFile.transferTo(new File(savePath));
        }
        boardDTO.setBoardFileName(boardFilename);
        //toBoardDTO 메서드에 회원엔티티를 같이 전달해야 함.
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            BoardEntity boardEntity = BoardEntity.toBoardDTO(boardDTO, memberEntity);
            Long id = boardRepository.save(boardEntity).getId();
            return id;
        }else {
            return null;
        }
    }


    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity board : boardEntityList){
            boardDTOList.add(BoardDTO.toBoardETO(board));
        }
        return boardDTOList;
    }

    @Transactional
    public BoardDTO findById(Long id) {
        // 조회수 처리
        boardRepository.boardHits(id);
        Optional<BoardEntity> optionalBoardEntity =boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            return BoardDTO.toBoardETO(optionalBoardEntity.get());
        }else {
            return null;
        }
    }


    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.toUpdate(boardDTO));
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber(); // 요청 페이지 값 가져옴.
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        // 삼항연산자
        page = (page == 1)? 0: (page-1);
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page<BoardEntity> => Page<BoardPagingDTO>
        Page<BoardDTO> boardList = boardEntities.map(
                // BoardEntity 객체 -> BoardDTO 객체 변환
                // board : BoardEntity 객체
                // new BoardDTO() 생성자
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                ));
        return boardList;
    }

    public List<BoardDTO> search(String q) {
        List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContaining(q);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList){
            boardDTOList.add(BoardDTO.toBoardETO(boardEntity));
        }
        return boardDTOList;

    }
}
