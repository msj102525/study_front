import React, { Component } from "react";
import BoardAxios from "../../axios/BoardAxios";
import withRouter from "../common/NavigateHOC";

// 컴포넌트 클래스는 반드시 리엑트의 Component 클래스를 상속받아야 함
// 컴포넌트는 한 페이지에 출력되는 내용을 담은 하나의 div 라고 생각하면 됨
// 여기서는 게시글 목록 출력 div 를 컴포넌트로 작성함
class ListBoardComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {  // state 필드 선언
            p_num: 1,
            paging: {},
            boards: []
        }
        this.createBoard = this.createBoard.bind(this);
    } // 생성자

    componentDidMount() {
        // mount : axios 로 서버로 요청하고 받은 결과를 컴포넌트와 연결시키는 것
        // axois : 자바스크립트 ajax 통신을 처리하는 모듈임
        BoardAxios.getBoardList()
            .then((res) => {
                console.log(res.data)
                this.setState({
                    // p_num: res.data.pagingData.currentPageNum,
                    // paging: res.data.pagingData,
                    boards: res.data
                });
            }).catch((error) => {
                console.error("There was an error fetching the board list!", error);
            });
    } // componentDidMount

    createBoard() {
        this.props.navigate("/boards/board/_create");
    } // createBoard

    readBoard(boardNum) {
        this.props.navigate(`/bdetail/${boardNum}`);
    } // readBoard

    // listBoard() {
    //     // console.log("pageNum : " + p_num);
    //     BoardAxios.getBoardList()
    //         .then((res) => {
    //             console.log(res.data);
    //             this.setState({
    //                 // p_num: res.data.pagingData.currentPageNum,
    //                 // paging: res.data.pagingData,
    //                 boards: res.data
    //             });
    //         });
    // } // listBoard

    viewPaging() {
        const pageNums = [];

        for (let i = this.state.paging.pageNumStart; i <= this.state.paging.pageNumEnd; i++) {
            pageNums.push(i);
        }

        return (pageNums.map((page) =>
            <li className="page-item" key={page.toString()} >
                <button className="page-link" onClick={() => this.listBoard(page)}>{page}</button>
            </li>
        ));
    }

    isPagingPrev() {
        if (this.state.paging.prev) {
            return (
                <li className="page-item">
                    <button className="page-link" onClick={() => this.listBoard((this.state.paging.currentPageNum - 1))}
                        tabindex="-1">Previous</button>
                </li>
            );
        }
    }

    isPagingNext() {
        if (this.state.paging.next) {
            return (
                <li className="page-item">
                    <button className="page-link" onClick={() => this.listBoard((this.state.paging.currentPageNum + 1))}
                        tabIndex="-1">Next</button>
                </li>
            );
        }
    }

    isMoveToFirstPage() {
        if (this.state.p_num !== 1) {
            return (
                <li className="page-item">
                    <button className="page-link" onClick={() => this.listBoard(1)} tabIndex="-1">Move to First Page</button>
                </li>
            );
        }
    }

    isMoveToLastPage() {
        if (this.state.p_num !== this.state.paging.pageNumCountTotal) {
            return (
                <li className="page-item">
                    <button className="page-link" onClick={() => this.listBoard((this.state.paging.pageNumCountTotal))}
                        tabIndex="-1">LastPage({this.state.paging.pageNumCountTotal})</button>
                </li>
            );
        }
    }

    // 출력 처리
    render() {
        return (
            <div style={{ padding: "40px 0", border: "1px solid #000" }}>
                <h2 className="text-center">Boards List</h2>
                <div className="row">
                    <button className="btn btn-primary" onClick={this.createBoard}>글 작성</button>
                </div>
                <div className="row" style={{ display: 'flex', justifyContent: 'center' }}>
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
                                            <td>
                                                <button onClick={() => this.readBoard(board.boardNum)} style={{ backgroundColor: 'lightblue', cursor: 'pointer', border: 'none', padding: '3px' }}>
                                                    {board.boardTitle}
                                                </button>
                                            </td>
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
                <div className="row">
                    <nav aria-label="Page navigation example">
                        <ul className="pagination justify-content-center">
                            {
                                this.isMoveToFirstPage()
                            }
                            {
                                this.isPagingPrev()
                            }
                            {
                                this.viewPaging()
                            }
                            {
                                this.isPagingNext()
                            }
                            {
                                this.isMoveToLastPage()
                            }
                        </ul>
                    </nav>
                </div>
            </div>
        ); // return
    } // render()
} // class

export default withRouter(ListBoardComponent);