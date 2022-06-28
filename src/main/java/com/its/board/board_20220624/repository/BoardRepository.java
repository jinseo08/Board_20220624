package com.its.board.board_20220624.repository;

import com.its.board.board_20220624.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // jpql(java persistence query Language) 방식: DB의 테이블 기준이 아니라 Entity 기준으로 쿼리문 작성
    // 테이블 기준 이였으면 'update board_table set board_hits = board_hits+1 where id=?'로 써줘야함
    // jpql 쓸 때는 반드시 별칭 써줘야 함
    // native sql 방식: update board_table set boardHits = boardHits+1 where id=?
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id= :id")
    void boardHits(@Param("id") Long id);

    // 검색 쿼리
    // select * from board_table where board_title Like '%?%'
    List<BoardEntity> findByBoardTitleContaining(String q);
}
