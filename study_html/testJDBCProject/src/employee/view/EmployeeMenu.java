package employee.view;

import java.util.ArrayList;
import java.util.Scanner;

import employee.controller.EmployeeController;
import employee.model.dto.Employee;

public class EmployeeMenu {
	// 의존성 주입 (Dependency Injection : DI)
	private static EmployeeController econtroller = new EmployeeController();
	private static Scanner sc = new Scanner(System.in);

	public static void displayMenu() {
		do {
			System.out.println("\n ********* 직원 관리 프로그램 *******\n");
			System.out.println("1. 전체 직원 출력");
			System.out.println("2. 사번으로 직원 조회");
			System.out.println("3. 새 직원 등록");
			System.out.println("4. 직원 정보 수정");
			System.out.println("5. 직원 삭제");
			System.out.println("6. 부서로 조회");
			System.out.println("7. 직급으로 조회");
			System.out.println("8. 이름으로 조회");
			System.out.println("9. 프로그램 끝내기");

			System.out.print("메뉴 번호 : ");
			int no = sc.nextInt();

			switch (no) {
			case 1:
				// ArrayList<Employee> list = econtroller.selectList();
				// printList(list); // 리스트 출력용
				printList(econtroller.selectList());
				break;
			case 2:
//				String empId = inputEmpId();
//				Employee emp = econtroller.selectOne(empId);
//				System.out.println("\n조회된 직원 정보는 다음과 같습니다.");
//				System.out.println(emp);
				try {
					System.out.println(econtroller.selectOne(inputEmpId()).toString());
					// null.메소드() 형태로 실행될 때, NullPointException 발생
					// null.toString()으로 실행시키면, NullPointException 이용할 수 있음
				} catch (NullPointerException e) {
					System.out.println("\n해당 사번에 대한 직원정보가 존재하지 않습니다.");
				}
				break;
			case 3:
				if(econtroller.insertEmployee(inputEmployee()) > 0) {
					System.out.println("\n새로운 직원 정보가 등록되었습니다");
				}else {
					System.out.println("\n새로운 직원 정보 등록에 실패했습니다. 관리자에게 문의하세요.");
				}
				break;
			case 4:
				if(econtroller.updateEmployee(modifyEmployee()) > 0) {
					System.out.println("\n직원 정보가 성공적으로 수정되었습니다");
				}else {
					System.out.println("\n직원 정보 수정에 실패했습니다. 관리자에게 문의하세요.");
				}
				break;
			case 5:
				if(econtroller.deleteEmployee(inputEmpId()) > 0) {
					System.out.println("\n해당 사번의 직원 정보가 삭제되었습니다");
				}else {
					System.out.println("\n직원 정보 삭제 실패했습니다. 관리자에게 문의하세요.");
				}
				break;
			case 6:
				printList(econtroller.selectDept(inputDeptId()));
				break;
			case 7:
				printList(econtroller.selectJob(inputJobId()));
				break;
			case 8:
				printList(econtroller.selectName(inputEmpName()));
				break;
			case 9:
				System.out.print("\n프로그램을 종료하시겠습니까? (Y/N) :");
				if (sc.next().toUpperCase().charAt(0) == 'Y') {
					return; // main 으로 리턴
				} else {
					break;
				}
			default:
				System.out.println("\n번호를 잘못 입력하였습니다.\n");
				System.out.println("확인하고 다시 입력");
			}

		} while (true);
	}

	// 직원 정보 수정할 내용 입력 리턴용
	private static Employee modifyEmployee() {
		Employee emp = new Employee();
		
		System.out.print("정보 수정할 직원의 사번");
		emp.setEmpId(sc.next());
		
		// 급여, 보너스포인트, 직급코드, 부서코드 수정 처리를 위해 입력받아 emp에 저장함
		System.out.print("급여 : ");
		emp.setSalary(sc.nextInt());
		System.out.print("보너스 포인트 : ");
		emp.setBonusPct(sc.nextDouble());
		System.out.print("직급 코드 : ");
		emp.setJobId(sc.next().toUpperCase());
		System.out.print("부서 코드 : ");
		emp.setDeptId(sc.next());
		
		return emp;
	}

	// 새로 등록할 직원 정보 입력 리턴용
	private static Employee inputEmployee() {
		Employee emp = new Employee();
		
		// 새로 등록할 직원 정보 입력 받음 : 사번 제외함
		System.out.print("이름 : ");
		emp.setEmpName(sc.next());
		System.out.print("주민번호 : ");
		emp.setEmpNo(sc.next());
		System.out.print("이메일 : ");
		emp.setEmail(sc.next());
		System.out.print("전화번호 (-빼고 입력): ");
		emp.setPhone(sc.next());
		System.out.print("급여 : ");
		emp.setSalary(sc.nextInt());
		System.out.print("보너스 포인트 : ");
		emp.setBonusPct(sc.nextDouble());
		System.out.print("직급 코드 : ");
		emp.setJobId(sc.next().toUpperCase());
		System.out.print("부서 코드 : ");
		emp.setDeptId(sc.next());
		System.out.print("결혼여부(기혼: Y/ 미혼: N : ");
		emp.setMarriage(sc.next().toUpperCase());
		System.out.print("관리자 사번 : ");
		emp.setMgrId(sc.next());
		System.out.print("입사일(yyyy-MM-dd) : ");
		emp.setHireDate(java.sql.Date.valueOf(sc.next()));
		
		return emp;
	}

	// 전체 직원 조회 메서드
	private static void printList(ArrayList<Employee> list) {
		// for 문 또는 for each 문 사용해서 연속으로 객체 하나씩 꺼내서 출력 처리
		System.out.println("\n현재 직원수 : " + list.size() + "명 입니다.\n");

		for (Employee emp : list) {
			System.out.println(emp/* .toString() */);
		}
	}

	// 사번 입력용 메서드
	private static String inputEmpId() {
		System.out.print("\n조회 또는 삭제할 직원의 사번 : ");
		return sc.next();
	}

	// 부서코드 입력용 메서드
	private static String inputDeptId() {
		System.out.print("\n조회할 부서코드 : ");
		return sc.next();
	}

	// 사번 입력용 메서드
	private static String inputJobId() {
		System.out.print("\n조회할 직급코드 : ");
		return sc.next().toUpperCase();
	}

	// 사번 입력용 메서드
	private static String inputEmpName() {
		System.out.print("\n조회할 직원 이름 : ");
		return sc.next();
	}
}
