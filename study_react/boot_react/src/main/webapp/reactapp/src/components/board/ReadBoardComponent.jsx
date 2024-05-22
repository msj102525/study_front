import React, { Component } from 'react';
import BoardAxios from '../../axios/BoardAxios';
import withRouter from '../common/NavigateHOC';


class ReadBoardComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            board: {}
        }
    }


    componentDidMount() {
        const { boardNum } = this.props.params;
        BoardAxios.getBoardDetail(boardNum)
            .then(res => {
                this.setState({ board: res.data });
            })
            .catch(error => {
                console.error(error);
            });
    }

    returnDate(cTime, uTime) {
        return (
            <div className="row">
                <label>생성일 : [ {cTime} ] / 최종 수정일 : [ {uTime} ] </label>
            </div>
        )
    }

    goToList() {
        this.props.navigate('/');
    }

    goToUpdate = (event) => {
        event.preventDefault();
        this.props.navigate(`/boards/board/${this.state.board.boardNum}`);
    }

    deleteView = async () => {
        if (window.confirm("정말로 글 삭제?\n 삭제된 글은 복구 불가능")) {
            const { boardNum } = this.props.params;
            BoardAxios.deleteBoard(boardNum)
                .then(res => {
                    console.log("delete result => " + JSON.stringify(res));
                    if (res.status === 200) {
                        this.props.navigate('/');
                    } else {
                        alert("글 삭제가 실패했습니다.");
                    }
                }).catch(error => {
                    console.log(error);
                })

        }

    }


    render() {
        return (
            <div>
                <div className="card col-md-6 offset-md-3">
                    <h3 className="text-center"> Read Detail</h3>
                    <div className="card-body">
                        <div className="row">
                            <label> Title </label> : {this.state.board.boardTitle}
                        </div>
                        <div className="row">
                            <label> Contents </label> : <br></br>
                            <textarea value={this.state.board.boardContent} readOnly />
                        </div >
                        <div className="row">
                            <label> boardNum </label>:
                            {this.state.board.boardNum}
                        </div>
                        {this.returnDate(this.state.board.boardDate, this.state.board.boardDate)}
                        <button className="btn btn-primary" onClick={this.goToList.bind(this)}
                            style={{ marginLeft: "10px" }}>글 목록으로 이동</button>
                        <button className="btn btn-info" onClick={this.goToUpdate} style={{ marginLeft: "10px" }}>글
                            수정</button>
                        <button className="btn btn-danger" onClick={() => this.deleteView()}
                            style={{ marginLeft: "10px" }}>글 삭제</button>

                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(ReadBoardComponent);