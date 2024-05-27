import axios from "axios";

// 서버로 jwt 토큰 요청과 인증 정보 처리를 담당하는 자바스크립트 코드
// Axios 객체 인스턴스 생성
const instance = axios.create({
    baseURL: "http://localhost:9999"
});

// 요청 인터셉터 추가
instance.interceptors.request.use(
    (config) => {
        // '/reissue' 요청은 인터셉터에서 엑세스 토큰을 추가하지 않도록 함
        // 'login' 요청시 토큰이 저장되도록 처리하기 위함
        // 또는 저장된 토큰을 확인해서 로그인 상태 확인을 하기 위함
        if (config.url != '/reissue'){
            const token = localStorage.getItem('token');
            if (token) { // 로그인 상태
                config.headers["Authorization"] = `Bearer ${token}`;
            }
        }
        return config;
    },
    error => Promise.reject(error)
);

const refreshToken = async() => {
    const refreshToken = localStorage.getItem("refresh");
    const response = await instance.post('/reissue', null, {
        headers: {
            'Authorization': `Bearer ${refreshToken}`
        }
    });
    const token = response.headers["authorization"] | response.headers["Authorization"];
    const pureToken = token.split(' ')[1];
    localStorage.setItem("token", pureToken); // 재발급된 엑세스토큰 저장
    return pureToken;

};

// 응답 인터셉터(intercepter : 중간에 가로채다) 추가 
// 응답올 때 작동되는 인터셉터임
instance.interceptors.response.use(
    response => response, // 정상 응답
    async (error) => { // 에러 조치
        const originalRequest = error.config;

        // 401 오류가 발생하고, 재시도 한 적이 없다면
        if(error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true; // 재시도로 변경
            // 토큰을 갱신하고 재시도
            const newAccessToken = await refreshToken();
            originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`
            // 원래 요청으로 다시 수행
            return instance(originalRequest);
        }
        return Promise.reject(error);
    }
);

export default instance;


