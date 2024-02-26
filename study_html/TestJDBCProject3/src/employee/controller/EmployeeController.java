package employee.controller;

import java.util.ArrayList;

import employee.exception.EmployeeException;
import employee.model.dto.Employee;
import employee.model.service.EmployeeService;

public class EmployeeController {
	// 의존성 주입 (Dependency Injection : Di)
	private EmployeeService empService = new EmployeeService();
	
	public EmployeeController() {}
	
	// Method
	public ArrayList<Employee> selectList() throws EmployeeException{
		// dao의 전체조회 메소드 호출함
		return empService.seletALL();
	}

	public Employee selectOne(String empId) throws EmployeeException {
		// dao의 직원 한 명 조회 메소드 호출함ㄴ
		return empService.selectEmployee(empId);
	}

	public ArrayList<Employee> selectDept(String deptId) throws EmployeeException {
		// dao의 부서코드로 직원 리스트 조회
		return empService.selectDeptList(deptId);
	}

	public ArrayList<Employee> selectJob(String jobId) throws EmployeeException {
		// dao의 직급코드로 직원 리스트 조회
		return empService.selectJobList(jobId);
	}

	public ArrayList<Employee> selectName(String empName) throws EmployeeException {
		// dao의 이름패턴으로 직원 리스트 조회
		return empService.selectEmpNameList(empName);
	}
	
	// DML 처리용 메소드 ---------------------------------------------------
	public int deleteEmployee(String empId) throws EmployeeException {
		// dao의 사번으로 직원 정보 삭제처리하는 매서드 호출
		return empService.delete(empId);
	}

	public int updateEmployee(Employee emp) throws EmployeeException {
		// dao 의 직원 정보 수정 처리용 메서드 호출
		return empService.update(emp);
	}

	public int insertEmployee(Employee emp) throws EmployeeException {
		// dao 의 새 직원 등록 처리용 메서드 호출
		return empService.insert(emp);
	}
}
