package employee.model.dto;

import java.sql.Date;

// Model 은 도메인 모델과 비즈니스 로직 처리용 모델로 구분됨
// 도메인 모델 : 데이터베이스의 사용할 테이블(엔티티)에 행 단위러 값을 저장하는 객체
// vo(value object) == dto(data transfer object) == entity == bean== do(domain object)

// vo 작성 조건
// 반드시 직렬화할 것
// 모든 필드는 반드시 private일 것 => 사용할 테이블의 컬럼임
// 기본(매개변수 없는) 생성자와 매개변수 있는 생성자 작성할 것
// 모든 필드에 대한 getters and setters 만들 것
// toString() overriding
// 필요시 hashCode(), equals(), clone() 오버라이딩 할 것(선택)
public class Employee implements java.io.Serializable{
	private static final long serialVersionUID = -6485478099399133514L;
	
	//Field
	private String empId;
	private String empName;
	private String empNo;
	private String email;
	private String phone;
	private java.sql.Date hireDate;
	private String jobId;
	private int salary;
	private double bonusPct;
	private String marriage;
	private String mgrId;
	private String deptId;
	
	// Constructor
	public Employee() {
		super();
	}
	
	public Employee(String empId, String empNo, String empName) {
		super();
		this.empId = empId;
		this.empNo = empNo;
		this.empName = empName;
	}
	
	public Employee(String empId, String empName, String empNo, String email, String phone, Date hireDate,
			String jobId, int salary, double bonusPct, String marriage, String mgrId, String deptId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empNo = empNo;
		this.email = email;
		this.phone = phone;
		this.hireDate = hireDate;
		this.jobId = jobId;
		this.salary = salary;
		this.bonusPct = bonusPct;
		this.marriage = marriage;
		this.mgrId = mgrId;
		this.deptId = deptId;
	}
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public java.sql.Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(java.sql.Date hireDate) {
		this.hireDate = hireDate;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public double getBonusPct() {
		return bonusPct;
	}
	public void setBonusPct(double bonusPct) {
		this.bonusPct = bonusPct;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empNo=" + empNo + ", email=" + email
				+ ", phone=" + phone + ", hireDate=" + hireDate + ", jobId=" + jobId + ", salary=" + salary
				+ ", bonusPct=" + bonusPct + ", marriage=" + marriage + ", mgrId=" + mgrId + ", deptId=" + deptId + "]";
	}
	
	
	
}
