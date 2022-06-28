package com.its.board.board_20220624.dto;

import com.its.board.board_20220624.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardPassword;
    private String boardContents;
    private int boardHits;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String boardFileName;
    private MultipartFile boardFile;


    public static BoardDTO toBoardETO(BoardEntity board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setBoardTitle(board.getBoardTitle());
        boardDTO.setBoardWriter(board.getBoardWriter());
        boardDTO.setBoardPassword(board.getBoardPassword());
        boardDTO.setBoardContents(board.getBoardContents());
        boardDTO.setBoardHits(board.getBoardHits());
        boardDTO.setCreatedTime(board.getCreatedTime());
        boardDTO.setUpdatedTime(board.getUpdatedTime());
        boardDTO.setBoardFileName(board.getBoardFileName());
        return boardDTO;
    }

    public BoardDTO(Long id, String boardTitle, String boardWriter, int boardHits, LocalDateTime createdTime) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardHits = boardHits;
        this.createdTime = createdTime;
    }

    public BoardDTO(String boardTitle, String boardWriter, String boardPassword, String boardContents) {
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardPassword = boardPassword;
        this.boardContents = boardContents;
    }
}
