import React, { Component } from "react";
import BoardAxios from "../../axiosApi/BoardAxios";

// 컴포넌트 클래스는 반드시 리엑트의 Component 클래스를 상속받아야 함
// 컴포넌트는 한 페이지에 출력되는 내용을 담은 하나의 div 라고 생각하면 됨
// 여기서는 게시글 목록 출력 div 를 컴포넌트로 작성함
class BoardDetail extends Component {
    constructor(props) {
        super(props)

        this.state = {  // state 필드 선언
            boards: [] 
        }
    } // 생성자

    componentDidMount() {
        // mount : axios 로 서버로 요청하고 받은 결과를 컴포넌트와 연결시키는 것
        // axois : 자바스크립트 ajax 통신을 처리하는 모듈임
        BoardAxios.getBoardDetail()
            .then((res) => {
                this.setState({ boards: res.data });
            }).catch((error) => {
                console.error("There was an error fetching the board list!", error);
            });
    }

    // 출력 처리
    render() {
        return (
            <div>
                <h2 className="text-center">Boards List</h2>
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>첨부파일</th>
                                <th>조회수</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.boards.map(
                                    board =>
                                        <tr key={board.boardNum}>
                                            <td> {board.boardNum} </td>
                                            {/* <a href="javascript:void(0)" onClick={() => BoardAxios.getBoardDetail(board.boardTitle)}> */}
                                                <td style={{ backgroundColor: 'lightblue', cursor: 'pointer' }} onClick={() => BoardAxios.getBoardDetail(board.boardNum)}> {board.boardTitle} </td>
                                            {/* </a> */}
                                            <td> {board.boardWriter} </td>
                                            <td> {board.boardDate} </td>
                                            <td> {board.boardOriginalFileName} </td>
                                            <td> {board.boardReadCount} </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        ); // return
    } // render()
} // class

export default BoardDetail;