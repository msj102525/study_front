import React, { Component } from "react";
import BoardAxios from "../../axiosApi/BoardAxios";
import withRouter from "../common/NavigateHOC";

class CreateBoardComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            type: '',
            title: '',
            contents: '',
            memberNo: ''
        }

        this.changeTypeHandler = this.changeTypeHandler.bind(this);
        this.changeTitleHandler = this.changeTitleHandler.bind(this);
        this.changeContentsHandler = this.changeContentsHandler.bind(this);
        this.changeMemberNoHandler = this.changeMemberNoHandler.bind(this);
        this.postBoardInsert = this.postBoardInsert.bind(this);
        this.cancel = this.cancel.bind(this)
    }

    changeTypeHandler = (event) => {
        this.setState({ type: event.target.value });
    }
    changeTitleHandler = (event) => {
        this.setState({ title: event.target.value });
    }
    changeContentsHandler = (event) => {
        this.setState({ contents: event.target.value });
    }
    changeMemberNoHandler = (event) => {
        this.setState({ memberNo: event.target.value });
    }

    postBoardInsert = (event) => {
        event.preventDefault();
        const { boardNum } = this.props.params

        let board = {
            boardTitle: this.state.title,
            boardContent: this.state.contents,
            boardWriter: this.state.memberNo
        };

        console.log("board => " + JSON.stringify(board));
        if (boardNum === '_create') {
            BoardAxios.postBoardInsert(board)
                .then(res => {
                    this.props.navigate("/boards/list");
                }).catch(error => {
                    console.error(error);
                });
        } else {
            BoardAxios.putBoardUpdate(boardNum, board)
                .then(res => {
                    this.props.navigate("/boards/list");
                }).catch(error => {
                    console.error(error);
                });
        }

    }

    cancel() {
        this.props.navigate("/");
    }

    getTitle() {
        const { boardNum } = this.props.params
        if (boardNum === '_create') {
            return <h3 className="text-center">새글을 작성해주세요</h3>
        } else {
            return <h3 className="text-center">{boardNum}글을 수정 합니다.</h3>
        }
    }

    componentDidMount() {
        const { boardNum } = this.props.params
        if (boardNum === '_create') {
            return
        } else {
            BoardAxios.getBoardDetail(boardNum)
            .then((res) => {
                let board = res.data;
                console.log("board => " + JSON.stringify(board));

                this.setState({
                    boardTitle: board.boardTitle,
                    boardContent: board.boardContent,
                    boardWriter: board.boardWriter
                });
            });
        }
    }


    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            {this.getTitle()}
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label> Type </label>
                                        <select placeholder="type" name="type" className="form-control"
                                            value={this.state.type} onChange={this.changeTypeHandler}>
                                            <option value="1">자유게시판</option>
                                            <option value="2">질문과 답변</option>
                                        </select>
                                    </div>
                                    <div className="form-group">
                                        <label> Title </label>
                                        <input type="text" placeholder="title" name="title" className="form-control"
                                            value={this.state.title} onChange={this.changeTitleHandler} />
                                    </div>
                                    <div className="form-group">
                                        <label> Contents </label>
                                        <textarea placeholder="contents" name="contents" className="form-control"
                                            value={this.state.contents} onChange={this.changeContentsHandler} />
                                    </div>
                                    <div className="form-group">
                                        <label> MemberNo </label>
                                        <input placeholder="memberNo" name="memberNo" className="form-control"
                                            value={this.state.memberNo} onChange={this.changeMemberNoHandler} />
                                    </div>
                                    <button className="btn btn-success" onClick={this.postBoardInsert}>Save</button>
                                    <button className="btn btn-danger" onClick={this.cancel}
                                        style={{ marginLeft: "10px" }}>Cancel</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(CreateBoardComponent);


