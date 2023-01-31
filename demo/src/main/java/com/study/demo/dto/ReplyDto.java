package com.study.demo.dto;


import com.study.demo.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyDto {
    private Integer id;
    private String content;
    private Integer boardid;

    public ReplyDto(Reply reply){
        this.id = reply.getId();
        this.content = reply.getContent();
    }
}
