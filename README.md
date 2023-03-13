# 비비 🌊 

 <img width="10%" alt="app_main" src="https://user-images.githubusercontent.com/65700842/220795518-a5196e30-8782-407d-bf65-b4685c28b7ef.png">

## 🤔 프로젝트 설명

> 바다가기전엔? 현재 해수욕장이 얼마나 혼잡한지 어떻게 가는지 확인헤보세요 :) 

<br>

### 💻 기술스택 
#### ▪️ Client
<p>
 <img src="https://img.shields.io/badge/Anroid-3DDC84?style=for-the-badge&logo=Android&logoColor=white">
 <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white">
 <img src="https://img.shields.io/badge/Retrofit2-3E4348?style=for-the-badge&logo=Square&logoColor=white">
 <img src="https://img.shields.io/badge/OkHttp-3E4348?style=for-the-badge&logo=Square&logoColor=white">
 <img src="https://img.shields.io/badge/MVVM-3DDC84?style=for-the-badge&logo=&logoColor=white">
 <img src="https://img.shields.io/badge/Coroutine-3DDC84?style=for-the-badge&logo=&logoColor=white">
 <img src="https://img.shields.io/badge/DataBinding-0F9D58?style=for-the-badge&logo=&logoColor=white">
 <img src="https://img.shields.io/badge/Hilt-0F9D58?style=for-the-badge&logo=&logoColor=white">
 <img src="https://img.shields.io/badge/TedPermission-0F9D58?style=for-the-badge&logo=&logoColor=white">
 <img src="https://img.shields.io/badge/DataStore-0F9D58?style=for-the-badge&logo=&logoColor=white">
</p>

#### ▪️ Server
<p>
 <img src="https://img.shields.io/badge/OpenAPI-40AEF0?style=for-the-badge&logo=&logoColor=white">
 <img src="https://img.shields.io/badge/KaKaoAPI-FFCD00?style=for-the-badge&logo=Kakao&logoColor=white">
</p>
<br>

### 🛠 구현 사항
##### 1️⃣ 해변 혼잡도 표시
###### Rest 통신을 통해 혼잡도 정보를 전달받아 표시하는 기능을 구현 하였습니다.

##### 2️⃣ 카카오맵 지도 화면 구성
###### 카카오맵 지도를 사용하여 맵화면을 구성하였습니다.

##### 3️⃣ 카카오맵 API를 통해 리스트 선택시 지도 이동 및 pin표시 기능 
###### 카카오맵 API의 키워드 검색 및 좌표계 변환하는 기능을 통해 리스트 선택시 해당 해변으로 지도가 이동하고 해당 좌표에 pin을 표시하는 기능을 구현 하였습니다.

##### 4️⃣ URL Scheme을 통한 길찾기 기능
###### URL Scheme을 통해 목적지 길안내 기능을 구현 하였습니다.

##### 5️⃣ 해수욕장 검색 기능
###### 검색을 통해 간편하게 해수욕장의 혼잡도 및 길안내를 받을 수 있도록 구현 하였습니다.

##### 6️⃣ 현재위치 표시
###### GPSProvider 와 Network Provider를 통한 현재 위치를 수신하여 지도에 표시하도록 구현 하였습니다.

##### 7️⃣ 현재 버전 체크
###### 현재 앱의 버전을 체크하는 화면을 구현 하였습니다.

##### 8️⃣ 기본 네비게이션 설정 기능
###### DataStore를 통해 사용자가 설정한 기본 네비게이션앱을 저장하여 해당 앱을 기반으로 길안내 기능을 제공하도록 구현 하였습니다.



<br>

### 🎥 시연 화면
<div align="center">
 <img width="30%" alt="app_main" src="https://user-images.githubusercontent.com/65700842/224614932-065ecda4-aafb-48c5-a1e8-c6060b05d7e0.gif">
</div>


### 😎 프로젝트 사용기술 설명
##### 1️⃣ Dagger Hilt를 활용하여 의존성을 주입 해주었습니다.
##### 2️⃣ MVVM 디자인 기반으로 프로젝트를 진행 하였습니다.
##### 3️⃣ Coroutine , Coroutine Flow를 통한 비동기 처리를 했습니다.
##### 4️⃣ Retrofit2를 통해 Rest통신을 하였습니다.
##### 5️⃣ Repository를 사용하여 Data를 관리 하였습니다.
##### 6️⃣ Clean Architecture를 위해 Module화를 통해 각 Layer를 분리해주었습니다.
##### 7️⃣ TedPermission 라이브러리를 통한 권한 체크를 했습니다.
##### 8️⃣ DataStore를 통해 사용자의 간단한 설정 정보를 저장하도록 했습니다.

