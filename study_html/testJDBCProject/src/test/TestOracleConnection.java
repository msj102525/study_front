package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestOracleConnection {

	public static void main(String[] args) {
		// 자바와 오라클 db 연결 테스트
		new TestOracleConnection().dbConnect();
	}

	public void dbConnect() {
		Connection conn = null;
		
		// jdbc coding 순서
		try {
			// 1. 드라이버 클래스 등록 : 다운받은 jdbc driver 라이브러리에 있음
			// 프로젝트 속성 >> java build path >> library 탭 >> add external jars로 추가
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Referenced Libraries에 있는 Class
			// 에러 없으면 등록 성공
			
			// 2. 오라클 dbms와 연결
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:xe",
					"c##student", "student");
			
			if(conn == null) {
				System.out.println("연결 실패! : " + conn);
			} else {
				System.out.println("연결 성공! : " + conn);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
			// 지정한 위치에 jdbc jar 파일이 없거나, 라이브러리 추가 안 한 경우
			// 클래스명 오타 있는 경우
		} catch (SQLException e) {
			e.printStackTrace();
			// url, user, password에 오타 있는 경우
			// 사용자계정이 잠겨 있는 경우 => 관리자계정에서 alter user를 unlock 처리하면 됨
		}finally {
			// 6. 사용이 끝나면 반드시 닫아야 함
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
