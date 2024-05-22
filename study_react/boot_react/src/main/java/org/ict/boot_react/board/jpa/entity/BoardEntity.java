package org.ict.boot_react.board.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.board.model.dto.BoardDto;

import java.sql.Date;
import java.util.GregorianCalendar;

//테이블 생성에 대한 가이드 클래스임
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="BOARD")
@Entity    //jpa 가 관리함, repository 와 연결됨
public class BoardEntity {
    @Id     //JPA 가 객체를 관리할 때 식별할 기본키 저장
//    @GeneratedValue(strategy = GenerationType.IDENTITY)   //primary key 지정
    //사용시 무조건 default 로 입력되므로 주석 처리할 것
    @Column(name = "BOARD_NUM", nullable = false)
    private int boardNum;
    @Column(name = "BOARD_WRITER", nullable = false, length = 50)
    private String boardWriter;
    @Column(name = "BOARD_TITLE", nullable = false, length = 50)
    private String boardTitle;
    @Column(name = "BOARD_CONTENT")
    private String boardContent;
    @Column(name = "BOARD_ORIGINAL_FILENAME")
    private String boardOriginalFileName;
    @Column(name = "BOARD_RENAME_FILENAME")
    private String boardRenameFileName;
    @Column(name = "BOARD_READCOUNT")
    private int boardReadCount;
    @Column(name = "BOARD_DATE")
    private java.util.Date boardDate;

    @PrePersist   //jpa 로 가기 전에 작동됨
    public void prePersist(){
        //boardDate 에 현재 날짜 적용
        boardDate = new Date(System.currentTimeMillis());
    }

    //entity --> dto 로 변환하는 메소드 추가함
    public BoardDto toDto(){
        return BoardDto.builder()
                .boardNum(this.boardNum)
                .boardWriter(this.boardWriter)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardOriginalFileName(this.boardOriginalFileName)
                .boardRenameFileName(this.boardRenameFileName)
                .boardReadCount(this.boardReadCount)
                .boardDate(this.boardDate)
                .build();
    }

}
