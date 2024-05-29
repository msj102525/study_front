import axios from "./AuthApi";
import { authStore } from "../stores/AuthStore";

export const signUp = (signUpData) => {
    return axios.post("/members", signUpData).then(res => {
        return res;
    });
};

export const login = (loginData) => {
    return axios.post("/api/auth", loginData).then(res => {
        // 로그인 성공시 토큰 처리
        const token = res.headers["authorization"] || res.headers["Authorization"];
        if(token) {
            const accessToken = token.split(" ")[1];
            window.localStorage.setItem("token", accessToken);
            window.localStorage.setItem("isAdmin", res.data.isAdmin);
            window.localStorage.setItem("token", res.data.refresh);
            authStore.setIsAdmin(res.data.isAdmin); // true | false 로 기록
            authStore.checkLoggedIn();
        }
        return res;
    });
};

export const logout = () => {
    return axios.post("/logout").then(res => {
        return res;
    });
};

// 마이페이지, 회원정보수정, 회원탈퇴
// 관리자 : 회원목록 조회, 회원 검색 (아이디, 가입날짜, 로그인가능여부 등)