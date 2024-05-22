import './App.css';
// 한 페이지에 여러 컴포넌트 배치 또는 출력시에는 반드시 라우넡 사용해야 함
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
// 이 페이지에 보여지게 할 컴포넌트들 임포트함
import ListBoardComponent from './components/board/ListBoardComponent';
import HeaderComponent from './components/common/HeaderComponent';
import FooterComponent from './components/common/FooterComponent';
import CreateBoardComponent from './components/board/CreateBoardComponent';
import ReadBoardComponent from './components/board/ReadBoardComponent';

function App() {
  return (
    <div className="App">
      <Router>
        <HeaderComponent />
        <div className="container">
          <Routes>
            <Route path="/"  element={<ListBoardComponent />}></Route>
            <Route path="/boards/list" element={<ListBoardComponent />}></Route>

            <Route path="/boards/board/:boardNum" element={<CreateBoardComponent />}></Route>
            <Route path="/bdetail/:boardNum" element={<ReadBoardComponent />}></Route>
          </Routes>          
        </div>
        <FooterComponent />
      </Router>
    </div>
  );
}

export default App;
