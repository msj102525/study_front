/**
 * main.js
 * 2024-02-27 작성됨
 */

// 전역변수 선언 : 함수 밖에서 선언된 변수
// 선언된 위치 아래쪽에서는 모두 사용 가능함
// var 변수명 = 값;
var flag = false;

function toggleImage() {
	// 지역변수
	var img = document.querySelector('#image1');

	if (!flag) { //flag == false
		/*img.src = "/testj/resources/images/Android.gif";*/
		img.setAttribute("src", "/testj/resources/images/Android.gif");

		flag = true;
	} else { // flag == true;
		/*img.src = "/testj/resources/images/button.jpg";*/
		img.setAttribute("src", "/testj/resources/images/button.jpg");

		flag = false;
	}

}

function changeCSS() {
	// 글자색, 테두리선, 글자크기, 배경색 변경 처리함
	var element = document.getElementById("demo3");
	var elGColor = element.style.backgroundColor;
	console.log(elGColor);
	element.style.color = "#ff00cc";
	element.style.border = "3px double black";
	element.style.fontSize = "24pt";
	element.style.backgroundColor = "lavender";
	console.log("Hello World!");
	console.log(element.style.backgroundColor);

	element.style.backgroundColor = elGColor;
}

function testAlert() {
	// window.alert("main.js 안에 작성된 testAlert() 함수 실행됨");
	alert("main.js 안에 작성된 testAlert() 함수 실행됨");
	// 자바스크립트 내장객체 중 최상위 객체인 window는 메소ㅗ드 사용시 생략할 수 있음
}

function calculator() {
	// input 태그에 기록된 값을 읽어올 때 value 속성을 사용함
	var value1 = document.getElementById('num1').value;
	var value2 = document.getElementById('num2').value;

	// 자바스크립트 내장함수 : typeof(변수 또는 값) => 값의 자료형 확인하는 함수임
	console.log("value1 : " + value1 + ", " + typeof (value1) + "\n");
	console.log("value2 : " + value2 + ", " + typeof (value2) + "\n");

	/*console.log(('b' + 'a' + '' + a + a).toUpperCase());*/
	// input 을 통해 입력들어오는 값은 모두 문자형임
	// 자바스크립트 내장함수 : Number(문자형숫자) => 숫자로 바꿈(파싱)

	var result = Number(value1) + Number(value2);
	document.getElementById('result').value = result;
	document.getElementById('demo4').innerHTML = "결과확인<br>" + result;
}

function checkType() {
	console.log(typeof("apple") + "\n"
	+ typeof 123 + "\n"
	+ typeof(2.3) + "\n"
	+ typeof("A") + "\n"
	+ typeof("7" + 12) + "\n"
	+ typeof(34 + "5") + "\n"
	+ typeof(3 == 5) + "\n"
	+ typeof(val)
	);
}





