package com.its.board.board_20220624.entity;

import com.its.board.board_20220624.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "boardTitle", length = 50, nullable = false) //nullable = false : not null 제약조건
    private String boardTitle;

    @Column(name = "boardWriter", length = 20)
    private String boardWriter;

    @Column(name = "boardPassword", length = 20)
    private String boardPassword;

    @Column(name = "boardContents", length = 500)
    private String boardContents;

    @Column(name = "boardHits")
    private int boardHits;

    @Column
    private String boardFileName;

    // 게시글 - 회원 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    // 게시글 - 댓글 연관관계
    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();



    // 회원 엔티티와 연관관계 맺기 전
//    public static BoardEntity toBoardDTO(BoardDTO boardDTO) {
//        BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
//        boardEntity.setBoardContents(boardDTO.getBoardContents());
//        boardEntity.setBoardHits(0);
//        boardEntity.setBoardFileName(boardDTO.getBoardFileName());
//        return boardEntity;
//    }


    //회원 엔티티와 연관관계 맺은 후
    public static BoardEntity toBoardDTO(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(memberEntity.getMemberEmail()); // 회원 이메일을 작성자로 한다면
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setBoardFileName(boardDTO.getBoardFileName());
        boardEntity.setMemberEntity((memberEntity));
        return boardEntity;
    }


    public static BoardEntity toUpdate(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter()); // 회원 이메일을 작성자로 한다면
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        return boardEntity;
    }
}
