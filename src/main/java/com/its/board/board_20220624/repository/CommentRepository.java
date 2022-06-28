package com.its.board.board_20220624.repository;

import com.its.board.board_20220624.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
