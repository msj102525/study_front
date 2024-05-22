package org.ict.boot_react.board.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.board.jpa.entity.BoardEntity;
import org.ict.boot_react.board.jpa.repository.BoardNativeVo;
import org.ict.boot_react.board.jpa.repository.BoardRepository;
import org.ict.boot_react.board.model.dto.BoardDto;
import org.ict.boot_react.board.util.PagingUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    //Service 에 대한 interface 를 만들어서, 상속받은 ServiceImpl 클래스를 만드는 구조로 작성해도 됨

    private final BoardRepository boardRepository;

    public ArrayList<BoardDto> selectTop3() {
        List<BoardNativeVo> nativeVoList = boardRepository.findTop3();
        ArrayList<BoardDto> list = new ArrayList<>();
        //내림차순정렬된 상위 3개만 추출함
        //JPQL 은 WHERE, GROUP BY 절에서만 서브쿼리 사용 가능함 => FROM 절에서 서브쿼리 사용 못 함
        for (int i = 0; i < 3; i++) {
            BoardDto boardDto = new BoardDto();
            boardDto.setBoardNum(nativeVoList.get(i).getBoard_num());
            boardDto.setBoardTitle(nativeVoList.get(i).getBoard_title());
            boardDto.setBoardReadCount(nativeVoList.get(i).getBoard_readcount());
            list.add(boardDto);
        }

        return list;
    }

    public ArrayList<BoardDto> selectList(Pageable pageable) {
        Page<BoardEntity> pages = boardRepository.findAll(pageable);
        ArrayList<BoardDto> list = new ArrayList<>();

        for (BoardEntity entity : pages) {
            BoardDto boardDto = entity.toDto();
            list.add(boardDto);
        }

        return list;
    }

    //board 테이블 총 목록 갯수 리턴
    public long getCountBoards() {
        return boardRepository.count();
    }

    //제목 검색에 대한 목록 갯수
    public long getCountSearchTitle(String keyword) {
        return boardRepository.countSearchTitle(keyword);
    }

    //작성자 검색에 대한 목록 갯수
    public long getCountSearchWriter(String keyword) {
        return boardRepository.countSearchWriter(keyword);
    }

    //등록날짜 검색에 대한 목록 갯수
    public long getCountSearchDate(java.sql.Date begin, java.sql.Date end) {
        return boardRepository.countSearchDate(begin, end);
    }

    public ArrayList<BoardDto> selectSearchTitle(String keyword, Pageable pageable) {
        Page<BoardEntity> pages = boardRepository.findSearchTitle(keyword, pageable);
        ArrayList<BoardDto> list = new ArrayList<>();

        for (BoardEntity entity : pages) {
            BoardDto boardDto = entity.toDto();
            list.add(boardDto);
        }

        return list;
    }

    public ArrayList<BoardDto> selectSearchWriter(String keyword, Pageable pageable) {
        Page<BoardEntity> pages = boardRepository.findSearchWriter(keyword, pageable);
        ArrayList<BoardDto> list = new ArrayList<>();

        for (BoardEntity entity : pages) {
            BoardDto boardDto = entity.toDto();
            list.add(boardDto);
        }

        return list;
    }

    public ArrayList<BoardDto> selectSearchDate(java.sql.Date begin, java.sql.Date end, Pageable pageable) {
        Page<BoardEntity> pages = boardRepository.findSearchDate(begin, end, pageable);
        ArrayList<BoardDto> list = new ArrayList<>();

        for (BoardEntity entity : pages) {
            BoardDto boardDto = entity.toDto();
            list.add(boardDto);
        }

        return list;
    }

    //게시글 상세 조회 처리용
    public BoardDto selectBoard(Integer boardNum) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardNum);
        BoardEntity boardEntity = optionalBoardEntity.get();

        //게시글 조회수 1증가 처리
        boardEntity.setBoardReadCount(boardEntity.getBoardReadCount() + 1);
        boardRepository.save(boardEntity);  //존재하는 글에 save 하면 수정됨

        return boardEntity.toDto();
    }

    public void insertBoard(BoardDto boardDto) {
        //데이터베이스 시퀀스객체를 사용하지 않는 경우
        // (BoardEntity  의 primary key 에 시퀀스 사용 등록 안 된 경우)
        //JPQL 에서는 WHERE 절과 GROUP BY 절에서만 서브쿼리 사용 가능함
        //SELECT 절과 FROM 절에서 서브쿼리 사용 못 함 => 서브쿼리 구문을 따로 작성해서 실행되게 하면 됨
        //마지막 게시글 번호 조회해 옴
        boardDto.setBoardNum(boardRepository.findLastBoardNum() + 1);
        boardRepository.save(boardDto.toEntity());
    }

    public void updateBoard(BoardDto boardDto) {
        BoardEntity boardEntity = boardRepository.getReferenceById(boardDto.getBoardNum());
        log.info("boardService.updateBoard : " + boardEntity);  //수정 글 확인
        boardEntity.setBoardTitle(boardDto.getBoardTitle());        //제목 수정
        boardEntity.setBoardContent(boardDto.getBoardContent());    //내용 수정
        boardEntity.setBoardReadCount(boardEntity.getBoardReadCount() + 1);  //조회수 1증가

        boardRepository.save(boardEntity);   //존재하는 글번호이면 수정임
    }


    public ResponseEntity<Map<String, Boolean>> deleteBoard(int boardNum) {
        Map<String, Boolean> response = new HashMap<>();
        if (!boardRepository.existsById(boardNum)) {
            response.put("Deleted Board Data by id : [" + boardNum + "]", Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            boardRepository.deleteById(boardNum);
            response.put("Deleted Board Data by id : [" + boardNum + "]", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }

    }

}
