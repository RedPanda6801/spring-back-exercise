package com.study.demo.dto;


import com.study.demo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDto {
    private Integer id;
    private String title;
    private String content;
    private String userId;

    public BoardDto(Board board){
        this.id = board.getId();
        this.content = board.getContent();
        this.title = board.getTitle();
        this.userId = board.getUser().getUserid();
    }
}
