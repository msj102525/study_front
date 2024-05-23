--id : 리프레시 토큰의 고유 식별자, CHAR(36), 필수입력
--user_id : 리프레시 토큰을 소유한 사용자의 아이디(이메일), VARCHAR2(50), 필수입력
--token_value : 리프레시 토큰 값, VARCHAR2(255), 필수입력
--issued_at : 토큰 발급 날짜 및 시작, TIMESTAMP, 기본값은 현재 시간
--expires_in : 토큰 만료 시간(초), BIGINT, 필수입력
--expiration_date : 토큰 만료 날짜 및 시간, TIMESTAMP, 필수입력
--user_agent : 토큰 발급 당시 사용된 사용자 에이전트, TEXT(LONG)
--status : 토큰 상태, VARCHAR2(50)
--제약조건 : PRIMARY KEY(id), FOREIGN KEY (user_id) REFERENCES 회원테이블명 (참조컬럼명) ON DELETE CASCADE
--인덱스 : 없음
DROP TABLE REFRESH_TOKEN CASCADE CONSTRAINTS;

CREATE TABLE REFRESH_TOKEN (
                               ID CHAR(36),
                               USERID VARCHAR2(50) NOT NULL,
                               TOKEN_VALUE VARCHAR(255) NOT NULL,
                               ISSUED_AT TIMESTAMP DEFAULT SYSTIMESTAMP,
                               EXPIRES_IN NUMBER NOT NULL,
                               EXPIRATION_DATE TIMESTAMP NOT NULL,
                               USER_AGENT LONG,
                               STATUS VARCHAR2(50),
                               CONSTRAINT PK_RTID PRIMARY KEY (ID),
                               CONSTRAINT FK_MUID FOREIGN KEY (USERID) REFERENCES MEMBER (USERID) ON DELETE CASCADE
);

COMMENT ON COLUMN REFRESH_TOKEN.ID IS '토큰식별ID';
COMMENT ON COLUMN REFRESH_TOKEN.USERID IS '토큰사용자아이디';
COMMENT ON COLUMN REFRESH_TOKEN.TOKEN_VALUE IS '토큰값';
COMMENT ON COLUMN REFRESH_TOKEN.ISSUED_AT IS '토큰생성날짜시간';
COMMENT ON COLUMN REFRESH_TOKEN.EXPIRES_IN IS '토큰만료초';
COMMENT ON COLUMN REFRESH_TOKEN.EXPIRATION_DATE IS '토큰만료날짜시간';
COMMENT ON COLUMN REFRESH_TOKEN.USER_AGENT IS '토큰발급에이전트';
COMMENT ON COLUMN REFRESH_TOKEN.STATUS IS '토큰상태';