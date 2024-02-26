package employee.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import employee.model.dto.Employee;
//import common.JDBCTemplate;
// 클래스가 가진 static 메소드만 임포트할 수 있음
// import static 패키지명.클래스명.스태틱메소드명;
// dao (database access object) : 비즈니스 로직 처리용 클래스 => jdbc 코드
//import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.*;

public class EmployeeDao {

	public EmployeeDao() {
	}

	// Method : 싫맹시킬 쿼리문 (sql문) 하나당 메소드 하나씩 작성함

	// 1. 직원 전체 조회
	public ArrayList<Employee> seletALL() {
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = "SELECT * FROM EMPLOYEE";

//		Connection conn = JDBCTemplate.getConnection();
		// import static 으로 불려온 메소드는 사용시 메소드명() 만 사용해야 함
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rset = null;

		try {
			// 1. 드라이브 등록
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//
//			// 2. dbms 연결
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
//			System.out.println("dba conn : " + conn); // 연결 확인용

			// 3. 문장 객체 생성하기 : Statement, PreparedStatement 사용
			// Statement 사용 ------------------
			stmt = conn.createStatement(); // 사용이 끝나면 반드시 닫아야 함

			// 4. 쿼리문 전송하고 결과받기
			// SELECT 쿼리문 전송용 메소드 : executeQuery(쿼리문장) : ResultSet 리턴함
			// DML 쿼리문 (INSERT, UPDATE, DELETE) : executeUpdate(쿼리문장) : int(실행된 행 갯수) 리턴
			rset = stmt.executeQuery(query); // 반드시 닫아야 함
//			System.out.println("dao selectAll() 실행 결과 확인 : " + rset);

			// 5. 받은 결과를 list에 옮기기 : 결과 매핑 처리함
			while (rset.next()) { // true 리턴 => 해당 위치에 행이 있다면
				// 행의 컬럼값 하나씩 꺼내서 Employee 객체에 저장하고
				// 변수 = rset.get필드자료형("컬럼명" | 컬럼나열순번);
				Employee emp = new Employee();

				emp.setEmpId(rset.getString("emp_id"));
				emp.setEmpName(rset.getString("emp_name"));
				emp.setEmpNo(rset.getString("emp_no"));
				emp.setEmail(rset.getString("email"));
				emp.setPhone(rset.getString("phone"));
				emp.setHireDate(rset.getDate("hire_date"));
				emp.setJobId(rset.getString("job_id"));
				emp.setSalary(rset.getInt("salary"));
				emp.setBonusPct(rset.getDouble("bonus_pct"));
				emp.setMarriage(rset.getString("marriage"));
				emp.setMgrId(rset.getString("mgr_id"));
				emp.setDeptId(rset.getString("dept_id"));

				// 리스트에 추가함
				list.add(emp);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
//			try {
//				// 6. 반드시 닫기 : 나중에 생성된 객체부터 먼저 닫기함
//				rset.close();
//				stmt.close();
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			JDBCTemplate.close(rset);
//			JDBCTemplate.close(stmt);
//			JDBCTemplate.close(conn);
			close(rset);
			close(stmt);
			close(conn);
		}

		return list;
	}

	// 2. 전달받은 사번으로 직원 한 명 조회해서 리턴
	public Employee selectEmployee(String empId) {
		Employee emp = null;

//		String query = "SELECT * FROM EMPLOYEE WHERE emp_id = '" + empId + "'";
		String query = "SELECT * FROM EMPLOYEE WHERE emp_id = ?";

//		Connection conn = null;
		Connection conn = getConnection();
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			// 3. 문장 객체 생성하기 : Statement, PreparedStatement 사용
			// Statement 사용 ------------------
//			stmt = conn.createStatement(); // 사용이 끝나면 반드시 닫아야 함
			// PreparedStatement 사용 ----------
			pstmt = conn.prepareStatement(query); // ?가 적용된 쿼리문을 가지고 문장 객체 생성함

			// 쿼리문에 사용된 ? 에 실제 값 적용함
			// 문장객체.set자료형(?순번, ?에 적용할 값 또는 변수);
			// ? 순번은 1부터 시작함
			pstmt.setString(1, empId);

			// 4. 쿼리문 전송하고 결과받기
			// SELECT 쿼리문 전송용 메소드 : executeQuery(쿼리문장) : ResultSet 리턴함
			// DML 쿼리문 (INSERT, UPDATE, DELETE) : executeUpdate(쿼리문장) : int(실행된 행 갯수) 리턴
//			rset = stmt.executeQuery(query); // 반드시 닫아야 함
			rset = pstmt.executeQuery();
//			System.out.println("dao selectAll() 실행 결과 확인 : " + rset);

			// 5. 받은 결과를 list에 옮기기 : 결과 매핑 처리함
			if (rset.next()) { // true 리턴 => 해당 위치에 행이 있다면
				// 행의 컬럼값 하나씩 꺼내서 Employee 객체에 저장하고
				// 변수 = rset.get필드자료형("컬럼명" | 컬럼나열순번);
				emp = new Employee(); // 조회된 행이 존재할 때만 객체 생성함

				emp.setEmpId(rset.getString("emp_id"));
				emp.setEmpName(rset.getString("emp_name"));
				emp.setEmpNo(rset.getString("emp_no"));
				emp.setEmail(rset.getString("email"));
				emp.setPhone(rset.getString("phone"));
				emp.setHireDate(rset.getDate("hire_date"));
				emp.setJobId(rset.getString("job_id"));
				emp.setSalary(rset.getInt("salary"));
				emp.setBonusPct(rset.getDouble("bonus_pct"));
				emp.setMarriage(rset.getString("marriage"));
				emp.setMgrId(rset.getString("mgr_id"));
				emp.setDeptId(rset.getString("dept_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}

		return emp;
	}

	// 6. 전달받은 부서코드로 해당 부서에 소속된 직원 리스트 리턴
	public ArrayList<Employee> selectDeptList(String deptId) {
		ArrayList<Employee> list = new ArrayList<Employee>();

//		String query = "SELECT * FROM EMPLOYEE WHERE dept_id = '" + deptId + "' ";
		String query = "SELECT * FROM EMPLOYEE WHERE dept_id = ?";

//		Connection conn = null;
		Connection conn = getConnection();
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			// 1. 드라이브 등록
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//
//			// 2. dbms 연결
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
////			System.out.println("dba conn : " + conn); // 연결 확인용

			// 3. 문장 객체 생성하기 : Statement, PreparedStatement 사용
			// Statement 사용 ------------------
//			stmt = conn.createStatement(); // 사용이 끝나면 반드시 닫아야 함
			pstmt = conn.prepareStatement(query);

			// ? 에 값 적용
			pstmt.setString(1, deptId);

			// 4. 쿼리문 전송하고 결과받기
			// SELECT 쿼리문 전송용 메소드 : executeQuery(쿼리문장) : ResultSet 리턴함
			// DML 쿼리문 (INSERT, UPDATE, DELETE) : executeUpdate(쿼리문장) : int(실행된 행 갯수) 리턴
//			rset = stmt.executeQuery(query); // 반드시 닫아야 함
			rset = pstmt.executeQuery();
//			System.out.println("dao selectAll() 실행 결과 확인 : " + rset);

			// 5. 받은 결과를 list에 옮기기 : 결과 매핑 처리함
			while (rset.next()) { // true 리턴 => 해당 위치에 행이 있다면
				// 행의 컬럼값 하나씩 꺼내서 Employee 객체에 저장하고
				// 변수 = rset.get필드자료형("컬럼명" | 컬럼나열순번);
				Employee emp = new Employee();

				emp.setEmpId(rset.getString("emp_id"));
				emp.setEmpName(rset.getString("emp_name"));
				emp.setEmpNo(rset.getString("emp_no"));
				emp.setEmail(rset.getString("email"));
				emp.setPhone(rset.getString("phone"));
				emp.setHireDate(rset.getDate("hire_date"));
				emp.setJobId(rset.getString("job_id"));
				emp.setSalary(rset.getInt("salary"));
				emp.setBonusPct(rset.getDouble("bonus_pct"));
				emp.setMarriage(rset.getString("marriage"));
				emp.setMgrId(rset.getString("mgr_id"));
				emp.setDeptId(rset.getString("dept_id"));

				// 리스트에 추가함
				list.add(emp);
			}

		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}
		return list;
	}

	// 7. 전달받은 직급코드로 해당 직급의 직원 리스트 리턴
	public ArrayList<Employee> selectJobList(String jobId) {
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = "SELECT * FROM EMPLOYEE WHERE job_id = ?";

//		Connection conn = null;
		Connection conn = getConnection();
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {

			// 3. 문장 객체 생성하기 : Statement, PreparedStatement 사용
			// Statement 사용 ------------------
//			stmt = conn.createStatement(); // 사용이 끝나면 반드시 닫아야 함
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, jobId);
			// 4. 쿼리문 전송하고 결과받기
			// SELECT 쿼리문 전송용 메소드 : executeQuery(쿼리문장) : ResultSet 리턴함
			// DML 쿼리문 (INSERT, UPDATE, DELETE) : executeUpdate(쿼리문장) : int(실행된 행 갯수) 리턴
//			rset = stmt.executeQuery(query); // 반드시 닫아야 함
			rset = pstmt.executeQuery();
//			System.out.println("dao selectAll() 실행 결과 확인 : " + rset);

			// 5. 받은 결과를 list에 옮기기 : 결과 매핑 처리함
			while (rset.next()) { // true 리턴 => 해당 위치에 행이 있다면
				// 행의 컬럼값 하나씩 꺼내서 Employee 객체에 저장하고
				// 변수 = rset.get필드자료형("컬럼명" | 컬럼나열순번);
				Employee emp = new Employee();

				emp.setEmpId(rset.getString("emp_id"));
				emp.setEmpName(rset.getString("emp_name"));
				emp.setEmpNo(rset.getString("emp_no"));
				emp.setEmail(rset.getString("email"));
				emp.setPhone(rset.getString("phone"));
				emp.setHireDate(rset.getDate("hire_date"));
				emp.setJobId(rset.getString("job_id"));
				emp.setSalary(rset.getInt("salary"));
				emp.setBonusPct(rset.getDouble("bonus_pct"));
				emp.setMarriage(rset.getString("marriage"));
				emp.setMgrId(rset.getString("mgr_id"));
				emp.setDeptId(rset.getString("dept_id"));

				// 리스트에 추가함
				list.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}

		return list;
	}

	// 8. 전달받은 이름 또는 이름패턴에 해당되는 직원 리스트 리턴
	public ArrayList<Employee> selectEmpNameList(String empName) {
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = "SELECT * FROM EMPLOYEE WHERE emp_name LIKE ?";

//		Connection conn = null;
		Connection conn = getConnection();
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			// 3. 문장 객체 생성하기 : Statement, PreparedStatement 사용
			// Statement 사용 ------------------
//			stmt = conn.createStatement(); // 사용이 끝나면 반드시 닫아야 함
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "%" + empName + "%");

			// 4. 쿼리문 전송하고 결과받기
			// SELECT 쿼리문 전송용 메소드 : executeQuery(쿼리문장) : ResultSet 리턴함
			// DML 쿼리문 (INSERT, UPDATE, DELETE) : executeUpdate(쿼리문장) : int(실행된 행 갯수) 리턴
//			rset = stmt.executeQuery(query); // 반드시 닫아야 함
			rset = pstmt.executeQuery();
//			System.out.println("dao selectAll() 실행 결과 확인 : " + rset);

			// 5. 받은 결과를 list에 옮기기 : 결과 매핑 처리함
			while (rset.next()) { // true 리턴 => 해당 위치에 행이 있다면
				// 행의 컬럼값 하나씩 꺼내서 Employee 객체에 저장하고
				// 변수 = rset.get필드자료형("컬럼명" | 컬럼나열순번);
				Employee emp = new Employee();

				emp.setEmpId(rset.getString("emp_id"));
				emp.setEmpName(rset.getString("emp_name"));
				emp.setEmpNo(rset.getString("emp_no"));
				emp.setEmail(rset.getString("email"));
				emp.setPhone(rset.getString("phone"));
				emp.setHireDate(rset.getDate("hire_date"));
				emp.setJobId(rset.getString("job_id"));
				emp.setSalary(rset.getInt("salary"));
				emp.setBonusPct(rset.getDouble("bonus_pct"));
				emp.setMarriage(rset.getString("marriage"));
				emp.setMgrId(rset.getString("mgr_id"));
				emp.setDeptId(rset.getString("dept_id"));

				// 리스트에 추가함
				list.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}

		return list;
	}

	// DML 처리용 메소드 구현부 -------------------------------------------------
	// 5. 전달받은 사번으로 해당 직원 정보 삭제
	public int delete(String empId) {
		int result = 0;

//		String query = "DELETE FROM EMPLOYEE WHERE emp_id = '" + empId + "'";
		String query = "DELETE FROM EMPLOYEE WHERE emp_id = ?";
//		Connection conn = null;
		Connection conn = getConnection();
//		Statement stmt = null;
		PreparedStatement pstmt = null;

		try {


			// 3. 문장 객체 만들기
//			stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, empId);

			// 4. 쿼리문 전송하고 결과받기
//			result = stmt.executeUpdate(query);
			result = pstmt.executeUpdate();

			// 5. 결과처리 : 트랜잭션 처리
			if (result > 0) { // 쿼리문 실행 성공시
				commit(conn);
			} else { // 쿼리문 실행 실패시
				rollback(conn);
			}

		} catch (Exception e) {
			// 모든 예외를 하나로 처리하게 다형성 이용함
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(conn);
		}

		return result;
	}

	// 3. 전달받은 직원정보로 새로운 행으로 추가 등록
	public int insert(Employee emp) {
		int result = 0;

//		String query = "INSERT INTO EMPLOYEE VALUES "
//				+ "(to_char(seq_empid.nextval), '" + emp.getEmpName()
//				+ "', '" + emp.getEmpNoString()
//				+ "', '" + emp.getEmail()
//				+ "', '" + emp.getPhone()
//				+ "', '" + emp.getHireDate()
//				+ "','" + emp.getJobId()
//				+ "'," + emp.getSalary()
//				+ " ," + emp.getBonusPct()
//				+ " , '" + emp.getMarriage()
//				+ "', '" + emp.getMgrId()
//				+ "', '" + emp.getDeptId()
//				+ "')";
		String query = "INSERT INTO EMPLOYEE VALUES " + "(to_char(seq_empid.nextval), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//		Connection conn = null;
		Connection conn = getConnection();
//		Statement stmt = null;
		PreparedStatement pstmt = null;

		try {

			// 3. 문장 객체 만들기
//			stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);

			// ?에 값 적용
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getEmpNo());
			pstmt.setString(3, emp.getEmail());
			pstmt.setString(4, emp.getPhone());
			pstmt.setDate(5, emp.getHireDate());
			pstmt.setString(6, emp.getJobId());
			pstmt.setInt(7, emp.getSalary());
			pstmt.setDouble(8, emp.getBonusPct());
			pstmt.setString(9, emp.getMarriage());
			pstmt.setString(10, emp.getMgrId());
			pstmt.setString(11, emp.getDeptId());

			// 4. 쿼리문 전송하고 결과받기
//			result = stmt.executeUpdate(query);
			result = pstmt.executeUpdate();

			// 5. 결과처리 : 트랜잭션 처리
			if (result > 0) { // 쿼리문 실행 성공시
				commit(conn);
			} else { // 쿼리문 실행 실패시
				rollback(conn);
			}

		} catch (Exception e) {
			// 모든 예외를 하나로 처리하게 다형성 이용함
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(conn);
		}

		return result;
	}

	// 4. 전달받은 직원 정보로 해당 직원 정보 수정처리
	public int update(Employee emp) {
		int result = 0;

//		String query = "UPDATE employee "
//				+ "SET salary = " + emp.getSalary()
//				+ ", bonus_pct = " + emp.getBonusPct()
//				+ ", job_id = '" + emp.getJobId() + "' "
//				+ ", dept_id =  '" + emp.getDeptId() + "' "
//				+ "WHERE emp_id = '"+ emp.getEmpId() + "' ";

		String query = "UPDATE employee " + "SET salary = ?" + ", bonus_pct = ?" + ", job_id = ?" + ", dept_id =  ?"
				+ "WHERE emp_id = ?";

//		Connection conn = null;
		Connection conn = getConnection();
//		Statement stmt = null;
		PreparedStatement pstmt = null;

		try {
			// 3. 문장 객체 만들기
//			stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, emp.getSalary());
			pstmt.setDouble(2, emp.getBonusPct());
			pstmt.setString(3, emp.getJobId());
			pstmt.setString(4, emp.getDeptId());
			pstmt.setString(5, emp.getEmpId());

			// 4. 쿼리문 전송하고 결과받기
//			result = stmt.executeUpdate(query);
			result = pstmt.executeUpdate();

			// 5. 결과처리 : 트랜잭션 처리
			if (result > 0) { // 쿼리문 실행 성공시
				commit(conn);
			} else { // 쿼리문 실행 실패시
				rollback(conn);
			}

		} catch (Exception e) {
			// 모든 예외를 하나로 처리하게 다형성 이용함
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(conn);
		}

		return result;
	}
}
