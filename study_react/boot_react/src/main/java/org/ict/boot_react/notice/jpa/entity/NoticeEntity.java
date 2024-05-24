package org.ict.boot_react.notice.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.notice.model.dto.NoticeDto;

import java.util.Date;
import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="NOTICE")
@Entity    //jpa 가 관리함, repository 와 연결됨
public class NoticeEntity {
    @Id     //JPA 가 객체를 관리할 때 식별할 기본키 지정하는 어노테이션임
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //primary key 지정하는 어노테이션
    @Column(name="NOTICENO")
    private int noticeNo;
    @Column(name="NOTICETITLE", nullable = false, length = 50)
    private String noticeTitle;
    @Column(name="NOTICEDATE", nullable = false)
    private Date noticeDate;
    @Column(name="NOTICEWRITER", nullable = false, length = 50)
    private String noticeWriter;
    @Column(name=" NOTICECONTENT", nullable = false, length = 2000)
    private String noticeContent;
    @Column(name="ORIGINAL_FILEPATH")
    private String originalFilePath;
    @Column(name="RENAME_FILEPATH")
    private String renameFilePath;
    @Column(name="IMPORTANCE", nullable = false, columnDefinition = "N")
    private String importance;
    @Column(name="IMP_END_DATE")
    private Date impEndDate;
    @Column(name="READCOUNT", nullable = false, columnDefinition = "1")
    private int readCount;

    @PrePersist     //jpa 로 넘어가기 전에 작동하라는 어노테이션임
    public void prePersist(){        
        noticeDate = new Date(System.currentTimeMillis());   //현재 날짜, 시간 적용
    }
    
    //entity -> dto 로 변환하는 메소드 추가
    public NoticeDto toDto(){
        return NoticeDto.builder()
                .noticeNo(noticeNo)
                .noticeTitle(noticeTitle)
                .noticeWriter(noticeWriter)
                .noticeContent(noticeContent)
                .noticeDate(noticeDate)
                .readCount(readCount)
                .originalFilePath(originalFilePath)
                .renameFilePath(renameFilePath)
                .importance(importance)
                .impEndDate(impEndDate.toString())
                .build();
    }
}
