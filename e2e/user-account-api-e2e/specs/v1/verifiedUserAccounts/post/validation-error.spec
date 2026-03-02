# 認証済みユーザーアカウントを追加する機能の仕様(異常)

## 検証に失敗した場合は400エラーのレスポンスが返される
* パス"/v1/verifiedUserAccounts"にボディ"src/test/resources/v1/verifiedUserAccounts/post/validation-error/request.json"ヘッダー"application/json"でリクエストを送信する
* レスポンスのステータスコードが"400"である
* DBの状態が変わらない
