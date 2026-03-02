# ユーザーアカウントを追加する機能の仕様

## ユーザーアカウントを追加する
* パス"/v1/unVerifiedUserAccounts"にボディ"src/test/resources/v1/unVerifiedUserAccounts/post/request.json"ヘッダー"application/json"でリクエストを送信する
* レスポンスのステータスコードが"204"である
* ユーザーアカウントがDBに登録されている
