## 개발환경
```
Android SDK 버전: Android 12(arm 64)
Android API 버전: API 31 
Device: Pixel 2 API 31(AVD)
```
## 라이브러리

| 라이브러리           | Version |
| ----------------- | :-----: |
| gson              | `2.8.8` |
| core-splashscreen | `1.0.0` |
| material          | `1.7.0` |

## 실행화면
| Login | SignUp | Main |
| ----- | :-----: | ----- |
|<img src = "./Screenshot_1.png" width ="250"> |<img src ="./Screenshot_2.png" width ="250">  |<img src = "./Screenshot_3.png" width ="250">|


## 구현 내용
1. 첫번째 화면(로그인 화면)
    - Relative Layout 사용
    - 앱 실행시 기존에 회원가입을 진행하여 계정이 존재할 때, 자동으로 ID, PW 입력 됨
    - 로그인 버튼을 클릭했을 때, ID와 PW가 입력되지 않았다면 입력을 요구,
         - 둘 다 입력이 되었을 때, ID가 기존에 등록된 것이라면 입력된 PW와 비교하여 로그인 성공, 비밀번호 불일치 출력
        - ID가 기존에 등록된 것이 아니면, 등록되지 않은 아이디라고 출력
    - 게스트 버튼을 클릭했을 때, 상품출력 액티비티로 바로 이동

2. 두번째 화면(회원가입 화면)
    - Linear Layout 사용
    - ID 중복검사
    - PW 조건검사(10자리 이상, 특수문자 1개이상 포함)
    - PW 조건검사는 PW입력을 갱신할 때 마다 EditText 바로 아래의 HelperText에서 실시간 확인 및 EditText의 테두리 색으로 직관적 확인
    - PW 입력시 EndIcon 을 클릭하면 입력한 패스워드를 문자로 볼 수 있음
    - 이름, 전화번호, 주소, 약관 동의
    - ID 중복검사, PW 조건검사, 모든 항목 채움, 약관 동의를 했을 때 Member 객체를 생성하고 preference에 저장 및 상품 페이지로 이동
    - preference에 HashMap<String, String>을 저장. 
        - 첫 번째 String은 ID
        - 두번 째 String은 Member객체(직접 정의한 유저 class)를 Json 형태로 변환한 String

3. 세번째 화면
    - Constraint Layout 사용
    - ListView를 사용하여 5개의 상품 이미지와 가격을 표시
    - 화면 아래부분에 회원정보 버튼을 클릭했을 때
        - 회원인 경우는 가입했을 때의 정보를 보여주고(Dialog)
        - 회원이 아닌 경우에는 회원가입을 할지 물어봄(Dialog)