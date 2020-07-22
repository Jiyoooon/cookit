# Git Base-rule

<br>

### 📝 Commit

- 기본적으로 commit은 `(날짜) | (커밋 내용) #(이슈 번호)`의 규칙을 따릅니다.<br>
  ex) 0720 | Create UserDetail.vue #S03P12A201-51
- 커밋의 시작은 보통 동사(원형)으로 합니다. (라고 합니다.)<br>
  ex) (If applied, this commit will) Fix typo (O) / Fixed typo (X)<br>
  출처 - https://meetup.toast.com/posts/106
- 대거 push가 아닌 이상 `git add .`의 사용은 지양해주세요.<br>
  VSCode에서 현재 로컬 저장소에서 수정된 파일이 다른 색깔로 표시될텐데, 귀찮겠지만 해당 파일만 어떤 내용으로 commit을 했는지 일일히 작업해주세요.<br>
  ex) git add front-end/src/components/UserDetail.vue

<br>

### 🛠 Merge Request

- remote 마스터로 push할 때는 ‼**본인의 git bash에서 바로 push하지 말고 gitlab의 `Merge Request`를 통해서 해주세요**‼ 제목 또한 commit 규칙을 따르면 됩니다.<br>
  ex) 0722 | Merge request #S03P12A201-51
- 설명에는 어떤 기능을 추가했는지 보는 사람이 이해할 정도로만 작성해주세요.
- Conflict 발생 시 해당 코드의 설명을 보거나 해당 코드를 작성한 사람과 협의(?) 후에 그 부분을 해결하고 merge하시면 됩니다.
