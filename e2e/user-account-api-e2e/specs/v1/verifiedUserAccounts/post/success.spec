# 認証済みユーザーアカウントを追加する機能の仕様(正常)

## 認証済みユーザーアカウントを作成する
* パス"/v1/verifiedUserAccounts"にボディ"src/test/resources/v1/verifiedUserAccounts/post/success/request.json"ヘッダー"application/json"でリクエストを送信する
* レスポンスのステータスコードが"204"である
* 未承認ユーザアカウントが認証済みユーザーアカウントとしてDBに登録されている
