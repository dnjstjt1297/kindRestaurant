## 착한 식당, 지금 찾기
이 프로젝트는 사용자가 서울시에서 가격이 저렴한 음식점 정보를 검색하고,

지도에서 위치를 선택하여 해당 위치를 기반으로 음식점을 검색할 수 있는 웹 입니다.

기능
1. 음식점 검색: 음식점 이름과 카테고리를 기반으로 음식점을 검색할 수 있습니다.
2. 지도에서 위치 선택: 지도에서 직접 위치를 클릭하여 해당 좌표를 기반으로 음식점을 검색할 수 있습니다.
3. 위치 자동 입력: 지도에서 선택한 위치의 좌표를 자동으로 입력 필드에 전달하여 검색할 수 있습니다.
4. 음식점 상세 정보: 음식점 주소, 영업시간, 사진, 지도 등등
5. 주변 공영주차장 위치 제공

## 기술 스택
1. Spring Boot, JPA: 백엔드 프레임워크
2. Thymeleaf: 서버 사이드 템플릿 엔진
3. Kakao Maps Api: 지도 서비스 제공
4. 서울시 열린데이터 광장 APi(착한업소, 공영주차장)
5. Mysql: 데이터 저장

## 사용 방법
1. 음식점 검색
   - `음식점 검색` 입력 필드에 음식점 이름을 입력합니다.
   - `카테고리` 드롭다운에서 원하는 카테고리를 선택합니다.
   - `지도`를 클릭하여 원하는 위치를 선택하고 주변 음식점들을 검색할 수 있습니다.
   ![image](https://github.com/user-attachments/assets/77ed8de4-25b0-4fcc-8a4e-471a493fb5d6)   [출처] 카카오맵 https://map.kakao.com/
   - Search 버튼을 클릭해 검색결과를 확인합니다.

2. 검색 결과
   ![image](https://github.com/user-attachments/assets/887d96e7-4e22-456c-8ada-c817a859a0d0)
   - 식당 상세보기 클릭

3. 음식점 정보
   ![image](https://github.com/user-attachments/assets/b462c73e-0582-48eb-8314-07682647e207)   [출처] 카카오맵 https://map.kakao.com/


## 프로젝트 링크
home.html: 사용자 인터페이스 및 위치 선택 페이지

search.html: 검색 결과 페이지

restaurant.html: 식당 정보 페이지


## Api 출처
1. 서울시 착한업소 Api: https://data.seoul.go.kr/dataList/OA-1173/S/1/datasetView.do

2. 서울시 공영주차장 Api: https://data.seoul.go.kr/dataList/OA-13122/S/1/datasetView.do

3. Kakao Maps Api: https://apis.map.kakao.com/

