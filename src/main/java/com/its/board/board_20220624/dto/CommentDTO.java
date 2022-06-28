package com.its.board.board_20220624.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Long boardId;

    public CommentDTO(String commentWriter, String commentContents) {
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
    }
}
