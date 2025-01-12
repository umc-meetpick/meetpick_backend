# meetpick_backend
<br/>

## 📃branch/issue rules

### 메인 브랜치 : `develop/`

- protection 걸려있음
- PR 날려서 리뷰 받지 않으면 merge 불가능

### 기능 별로 이슈 및 브랜치 따로 생성

- **이슈명** : `[분류]/이슈명/이슈 번호`
    - 분류 : `feat`, `bug`, `fix`, `refact`, … 기타 git commit rule 참고
    - 예시 :
    `[feat] 로그인 기능 개발/1` , `[bug] 검색 안되는 현상/2` , `[fix] 검색 버그 해결/3` , `[refact] 조회 성능 개선/4`, …
    - tag 지정하기
- **브랜치명** : `분류/개발내용/이슈명`
    - 예시 :
    `feat/login/1`, `fix/search/2`, `refact/search/3`, …

### 기능 완성되면 **`develop` 브랜치로 PR** 날리기

- 한 명 이상 리뷰 시 Merge 가능
- **Squash Merge 하기** (해당 브랜치 모든 커밋을 하나의 커밋으로 압축해서 Merge)

<br/>


## 🖊️커밋 컨벤션 -Commit Convention


| Type | Description |
|------|-------------|
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| add | 파일 추가 |
| update | 기능 업데이트 |
| del | 파일 삭제 |
| comment | 주석 수정|
| refactor | 코드 리팩토링 |
| docs | 문서 수정 |
| rename | 파일 혹은 폴더명 수정 |
| remove | 파일 삭제 |
| build | 빌드 파일 수정 |
| ci | CI/CD 설정 변경 |


