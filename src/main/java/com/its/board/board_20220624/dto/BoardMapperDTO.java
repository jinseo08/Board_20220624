package com.its.board.board_20220624.dto;

import lombok.Data;

@Data
public class BoardMapperDTO {
    private Long board_id;
    private String board_writer;
    private String board_title;
    private String board_contents;
}
