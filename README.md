# BE - Readme

## 프로젝트 기능

### 😊 회원가입 & 로그인

> 회원가입 후 같은 정보로 로그인을 할 수 있습니다. 로그인에 성공하여 토큰을 발급 받아야만 모든 페이지를 정상적으로 이용할 수 있습니다.
> 
<img width="700" alt="image" src="https://user-images.githubusercontent.com/51226340/222370232-476ca711-f632-472d-89a6-ca82b72b3c4e.png">
<img width="700" alt="image" src="https://user-images.githubusercontent.com/51226340/222370351-6402b328-362c-423f-a639-c2d86a9252ec.png">


### 🌟 회원 목록 조회

> home 화면에서 RollingRolling을 이용하는 모든 사용자의 rolling 페이지를 한 눈에 모아볼 수 있습니다. 
그 중에 찾고자 하는 회원이 있다면 알고 있는 닉네임으로 해당 회원의 Rolling 사이트를 조회할 수 있습니다.
> 
<img width="700" alt="image" src="https://user-images.githubusercontent.com/51226340/222370590-b7321a45-b190-458a-b942-3fbe7c2a8742.png">


### ✏️ Post & Comment

> 로그인에 성공한 유저라면 누구나 원하는 회원의 페이지에 방문해서 해당 페이지에 남겨진 질문과 답변을 조회할 수 있습니다. 
직접 질문을 남길 때는 로그인한 닉네임으로 질문을 남길지 익명으로 남길지 선택할 수 있으며, 본인이 작성한 질문은 ‘내가 남긴 질문’ 단락에 따로 분리되어 표시됩니다. 질문 작성자는 페이지 주인이 질문에 답변을 남기기 전까지 질문을 수정 및 삭제를 할 수 있지만, 답변이 완료되면 수정과 삭제를 모두 할 수 없습니다.
자신의 페이지에 남겨진 질문에는 본인만 답변을 남길 수 있으며, 원치 않는 질문은 삭제할 수 있습니다.
> 

### ❤️ 좋아요 기능

> 방문자의 질문, 사이트 주인의 답변, 그리고 유저의 프로필에 각각 좋아요를 추가할 수 있습니다. 이미 좋아요를 한 상태에서 한 번 더 좋아요를 등록하면 좋아요가 취소되어 값이 반환됩니다.
> 
<img width="546" alt="image" src="https://user-images.githubusercontent.com/51226340/222370888-f9fae13d-de24-4e4e-a588-3961fefe4a80.png">

### 👨‍💻 마이페이지 기능 (내 정보 수정) - 정환

> 마이페이지에서 프로필사진, 자기소개, 닉네임, 비밀번호를 수정할 수 있습니다. 비밀번호 수정란을 공백으로 둘 경우, 비밀번호는 변경하지 않고 나머지 항목만 수정할 수 있습니다.

<img width="700" alt="image" src=https://user-images.githubusercontent.com/51226340/222369332-84f9953f-845c-4ee4-ab3c-4f7933f597ea.png">
<img width="700" alt="image" src=https://user-images.githubusercontent.com/51226340/222369388-bb419fef-01a4-46ec-ad96-1f9e1a2bc204.png">


    

### ☑  페이징 처리

> 메인페이지에서 여러 회원들의 리스트가 페이징 처리되어 출력 됩니다.
회원의 개인 게시판에서 게시글들이 페이징 처리되어 출력 됩니다.
> 
- 미리보기

### 📩 실시간 메일 전송 - 정환

> 회원 가입시 회원 가입이 완료되었다는 메시지가 등록한 이메일로 전송됩니다.
> 
- 미리보기
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/87be44d7-c55d-419e-a02f-46971dd674cd/Untitled.png)
    

## 적용 기술

### ◻ Swagger - 경연

> 프론트엔드와 정확하고 원활한 소통을 위해 스웨거를 도입하여 적용하였습니다.
> 

### ◻ Spring Security - 정환

> 사용자 인증, 인가 기능 구현을 위해 Spring Security를 사용하였습니다.
> 

### ◻ S3를 통한 이미지 업로드 - 정환

> 이미지를 업로드 하기 위해 S3를 활용하였습니다
> 

### ◻ Java Mail Sending - 정환

> 특정 Service 수행 시 메일 전송이 가능해졌습니다.
> 

### ◻ ec2 연결 - 지윤

> AWS ec2 서버를 열어 제공하였습니다.
> 

### ◻ rds db 연결 - 영석 , 경연

공통적으로 db를 사용 할 수 있게 활용 하였습니다

## ⚙ Development Environment

`Java 17` `SpringBoot 2.7.9` `Ubuntu 20.04.5 LTS` 

## 🚨 Trouble Shooting

### 

### CORS problem

### entity 외래키 이슈

### application.properties관리 문제

### 배포관련 문제

### API 명세서 관리 문제

## 🙋💭 Concern

### 

## 🌐 Architecture

## 📋 ERD Diagram

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bb36dcda-01f4-4617-a9e5-9bb105685430/Untitled.jpeg)

## 📝 Technologies & Tools (BE) 📝
