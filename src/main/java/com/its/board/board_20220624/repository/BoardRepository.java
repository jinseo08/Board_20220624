package com.its.board.board_20220624.repository;

import com.its.board.board_20220624.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
