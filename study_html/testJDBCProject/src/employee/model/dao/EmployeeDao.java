package employee.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import employee.model.dto.Employee;

// dao (database access object) : 비즈니스 로직 처리용 클래스 => jdbc 코드

public class EmployeeDao {

	public EmployeeDao() {
	}

	// Method : 싫맹시킬 쿼리문 (sql문) 하나당 메소드 하나씩 작성함

	// 1. 직원 전체 조회
	public ArrayList<Employee> seletALL() {
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = "SELECT * FROM EMPLOYEE";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 6. 반드시 닫기 : 나중에 생성된 객체부터 먼저 닫기함
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 전달받은 사번으로 직원 한 명 조회해서 리턴
	public Employee selectEmployee(String empId) {
		Employee emp = null;

		String query = "SELECT * FROM EMPLOYEE WHERE emp_id = '" + empId + "'";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 6. 반드시 닫기 : 나중에 생성된 객체부터 먼저 닫기함
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return emp;
	}

	// 전달받은 부서코드로 해당 부서에 소속된 직원 리스트 리턴
	public ArrayList<Employee> selectDeptList(String deptId) {
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = "SELECT * FROM EMPLOYEE WHERE dept_id = '" + deptId + "' ";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 6. 반드시 닫기 : 나중에 생성된 객체부터 먼저 닫기함
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 전달받은 직급코드로 해당 직급의 직원 리스트 리턴
	public ArrayList<Employee> selectJobList(String jobId) {
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = "SELECT * FROM EMPLOYEE WHERE job_id = '" + jobId + "' ";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 6. 반드시 닫기 : 나중에 생성된 객체부터 먼저 닫기함
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 전달받은 이름 또는 이름패턴에 해당되는 직원 리스트 리턴
	public ArrayList<Employee> selectEmpNameList(String empName) {
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = "SELECT * FROM EMPLOYEE WHERE emp_name LIKE '%" + empName + "%'";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 6. 반드시 닫기 : 나중에 생성된 객체부터 먼저 닫기함
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// DML 처리용 메소드 구현부 -------------------------------------------------
	// 전달받은 사번으로 해당 직원 정보 삭제
	public int delete(String empId) {
		int result = 0;

		String query = "DELETE FROM EMPLOYEE WHERE emp_id = '" + empId + "'";

		Connection conn = null;
		Statement stmt = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
			// 자동 커밋 설정 해제함 (반드시)
			conn.setAutoCommit(false);

			// 3. 문장 객체 만들기
			stmt = conn.createStatement();

			// 4. 쿼리문 전송하고 결과받기
			result = stmt.executeUpdate(query);

			// 5. 결과처리 : 트랜잭션 처리
			if (result > 0) { // 쿼리문 실행 성공시
				conn.commit();
			} else { // 쿼리문 실행 실패시
				conn.rollback();
			}

		} catch (Exception e) {
			// 모든 예외를 하나로 처리하게 다형성 이용함
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

	// 전달받은 직원정보로 새로운 행으로 추가 등록
	public int insert(Employee emp) {
		int result = 0;

		String query = "INSERT INTO EMPLOYEE VALUES "
				+ "(to_char(seq_empid.nextval), '" + emp.getEmpName()
				+ "', '" + emp.getEmpNoString()
				+ "', '" + emp.getEmail()
				+ "', '" + emp.getPhone()
				+ "', '" + emp.getHireDate()
				+ "','" + emp.getJobId()
				+ "'," + emp.getSalary()
				+ " ," + emp.getBonusPct()
				+ " , '" + emp.getMarriage()
				+ "', '" + emp.getMgrId()
				+ "', '" + emp.getDeptId()
				+ "')";

		Connection conn = null;
		Statement stmt = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
			// 자동 커밋 설정 해제함 (반드시)
			conn.setAutoCommit(false);

			// 3. 문장 객체 만들기
			stmt = conn.createStatement();

			// 4. 쿼리문 전송하고 결과받기
			result = stmt.executeUpdate(query);

			// 5. 결과처리 : 트랜잭션 처리
			if (result > 0) { // 쿼리문 실행 성공시
				conn.commit();
			} else { // 쿼리문 실행 실패시
				conn.rollback();
			}

		} catch (Exception e) {
			// 모든 예외를 하나로 처리하게 다형성 이용함
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

	// 전달받은 직원 정보로 해당 직원 정보 수정처리
	public int update(Employee emp) {
		int result = 0;

		String query = "UPDATE employee "
				+ "SET salary = " + emp.getSalary()
				+ ", bonus_pct = " + emp.getBonusPct()
				+ ", job_id = '" + emp.getJobId() + "' "
				+ ", dept_id =  '" + emp.getDeptId() + "' "
				+ "WHERE emp_id = '"+ emp.getEmpId() + "' ";

		Connection conn = null;
		Statement stmt = null;

		try {
			// 1. 드라이브 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. dbms 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "c##student", "student");
			// 자동 커밋 설정 해제함 (반드시)
			conn.setAutoCommit(false);

			// 3. 문장 객체 만들기
			stmt = conn.createStatement();

			// 4. 쿼리문 전송하고 결과받기
			result = stmt.executeUpdate(query);

			// 5. 결과처리 : 트랜잭션 처리
			if (result > 0) { // 쿼리문 실행 성공시
				conn.commit();
			} else { // 쿼리문 실행 실패시
				conn.rollback();
			}

		} catch (Exception e) {
			// 모든 예외를 하나로 처리하게 다형성 이용함
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}
}
