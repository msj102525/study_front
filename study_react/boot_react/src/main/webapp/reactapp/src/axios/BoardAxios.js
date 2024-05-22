import axios from "axios";

// 기본 URL 지정
const BOARD_BASE_URL = 'http://localhost:9999/boards';

class BoardAxios {
    // 게시글 목록 조회 요청 처리용
    getBoardList(p_num) {
        console.log(p_num);
        // return axios.get(`${BOARD_BASE_URL}/list?page=${p_num}&limit=10`);
        return axios.get(`${BOARD_BASE_URL}/list?page=1&limit=10`);
    }

    // 게시글 상세보기 요청 처리용
    getBoardDetail(boardNum) {
        console.log(boardNum);
        return axios.get(`${BOARD_BASE_URL}/bdetail/${boardNum}`);
    }

    // 새 게시글 등록 처리용
    postBoardInsert(board) {
        return axios.post(`${BOARD_BASE_URL}/board`, board)
            .then(res => {
                console.log(res.data);
            }).catch(err => {
                console.log(err);
            });
    }

    // 게시글 수정 처리용
    putBoardUpdate(boardNum, board) {
        return axios.put(`${BOARD_BASE_URL}/${boardNum}`, board);
    }

    // 게시글 삭제 처리용
    deleteBoard(boardNum) {
        return axios.delete(`${BOARD_BASE_URL}/${boardNum}`);
    }

    // 게시글 제목 검색 요청 처리용
    getBoardTitle(title) {
        
    }

    // 게시글 작성자 검색 요청 처리용
    getBoardWriter(writer) {
        
    }

    // 게시글 날짜 검색 요청 처리용
    getBoardDate(date) {
        
    }
}

const boardAxiosInstance = new BoardAxios();
export default boardAxiosInstance; // 객체를 내보냄
