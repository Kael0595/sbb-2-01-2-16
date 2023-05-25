package com.ll.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
//404 오류에 상태코드, 이유는 entity not found
public class DataNotFoundException extends RuntimeException {
    //data가 없을때 발생하는 예외처리
    //java에 기본 내장된 RuntimeException을 상속받아 생성한 사용자 정의 예외 클래스
    //RuntimeException : 프로그래밍 오류나 예기치 못한 오류에 대한 예외 처리
    private static final long serialVersionUID = 1L;
    //java 직렬화(Serialization)에서 사용되는 정적 필드
    //java 직렬화 : 객체를 스트림으로 변환하여 저장하거나 네트워크를 통해 전송할 수 있는 기능을 제공하는 방법

    public DataNotFoundException(String message) {
        super(message);
        //부모(RuntimeException)에 정의된 오류메시지 출력에 String으로 받은 message 출력
    }


}
