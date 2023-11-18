# 구름 출석부 관리 Web Application - Backend

## 프로젝트 소개
이 프로젝트는 구름 출석부 관리 웹앱의 백엔드 부분을 담당합니다.  
본 애플리케이션은 사용자들이 온라인으로 출석 관리를 수행할 수 있도록 돕는 웹 기반 솔루션입니다.

## 기능
- **출석 기록**: 사용자 출석 상태 (출석, 결석, 조퇴 등) 기록 및 관리.
- **사용자 관리**: 사용자 계정 (관리자 및 학생) 생성, 수정, 삭제.
- **출석 데이터 보고**: 출석 상태에 대한 보고 및 분석 기능.

## 기술 스택
- **Spring Boot**: 애플리케이션 프레임워크 및 서버 환경.
- **Spring Data JPA**: 데이터베이스 상호작용 및 ORM.
- **MySQL**: 데이터베이스 관리 시스템.


## 시작하기
로컬 개발 환경에서 프로젝트 설정:

1. **소스 코드 복제**:
- `git clone https://github.com/study-from-goorm/Attendance_Management_System_Backend.git`


2. **필요한 설정 구성**:
- `application.properties`에서 데이터베이스 연결 설정을 확인 및 수정.
- 필요한 경우, `secret.properties` 파일을 생성하여 민감한 정보 관리.
  - `spring.datasource.username=yourname`
  - `spring.datasource.password=yourpassword`


## 기여 방법

1. 이 레포지토리를 포크합니다.
2. 기능 브랜치를 만듭니다 (`git checkout -b feature/AmazingFeature`).
3. 변경 사항을 커밋합니다 (`git commit -m 'Add some AmazingFeature'`).
4. 브랜치에 푸시합니다 (`git push origin feature/AmazingFeature`).
5. Pull Request를 생성합니다.
