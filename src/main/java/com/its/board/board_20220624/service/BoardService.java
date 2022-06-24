package com.its.board.board_20220624.service;

import com.its.board.board_20220624.dto.BoardDTO;
import com.its.board.board_20220624.entity.BoardEntity;
import com.its.board.board_20220624.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Long save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toBoardDTO(boardDTO);
        Long id = boardRepository.save(boardEntity).getId();
        return id;
    }


    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity board : boardEntityList){
            boardDTOList.add(BoardDTO.toBoardETO(board));
        }
        return boardDTOList;
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity =boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            return BoardDTO.toBoardETO(optionalBoardEntity.get());
        }else {
            return null;
        }
    }
}