package com.its.board.board_20220624.repository;

import com.its.board.board_20220624.dto.BoardDTO;
import com.its.board.board_20220624.dto.BoardMapperDTO;
import com.its.board.board_20220624.entity.BoardEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapperRepository {
    List<BoardMapperDTO> boardList();

    void save(BoardMapperDTO boardMapperDTO);

}
