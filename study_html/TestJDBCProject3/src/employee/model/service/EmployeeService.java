package employee.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import employee.exception.EmployeeException;
import employee.model.dao.EmployeeDao;
import employee.model.dto.Employee;

// Connection 관리와  트랜잭션 관리가 목적인 모델 서비스 클래스임
public class EmployeeService {
	// 의존성 주입 (Dependency Injection : Di)
		private EmployeeDao empDao = new EmployeeDao();

		public ArrayList<Employee> seletALL() throws EmployeeException {
			Connection conn = getConnection();
			ArrayList<Employee> list = empDao.seletALL(conn);
			close(conn);
			
			return list;
			
		}

		public Employee selectEmployee(String empId) throws EmployeeException {
			Connection conn = getConnection();
			
			Employee emp = empDao.selectEmployee(conn, empId);
			close(conn);
			
			return emp;			
		}

		public ArrayList<Employee> selectDeptList(String deptId) throws EmployeeException {
			Connection conn = getConnection();
			ArrayList<Employee> list = empDao.selectDeptList(conn, deptId);
			close(conn);
			
			return list;
		}

		public ArrayList<Employee> selectJobList(String jobId) throws EmployeeException {
			Connection conn = getConnection();
			ArrayList<Employee> list = empDao.selectJobList(conn, jobId);
			close(conn);
			
			return list;
		}

		public ArrayList<Employee> selectEmpNameList(String empName) throws EmployeeException {
			Connection conn = getConnection();
			ArrayList<Employee> list = empDao.selectEmpNameList(conn, empName);
			close(conn);
			
			return list;
		}

		//DML 처리용 메소드 : 트랜잭션 처리 반드시 필요함
		public int delete(String empId) throws EmployeeException {
			Connection conn = getConnection();
			int result = empDao.delete(conn, empId);
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
			close(conn);
			return result;
		}

		public int update(Employee emp) throws EmployeeException {
			Connection conn = getConnection();
			int result = empDao.update(conn, emp);
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
			close(conn);
			return result;
		}

		public int insert(Employee emp) throws EmployeeException {
			Connection conn = getConnection();
			int result = empDao.insert(conn, emp);
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
			close(conn);
			return result;
		}
}
