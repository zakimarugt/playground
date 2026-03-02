namespace UserAccountApi.Infrastructure

open Dapper.FSharp.MySQL
open MySqlConnector

type UserAccountDb(connection: MySqlConnection) =
    member this.connection = connection

module UserAccountDb =
    let userAccountsTable = table'<UserAccount> "user_accounts"
    let verifiedUserAccountsTable = table'<VerifiedUserAccountRecord> "verified_user_accounts"
    let unVerifiedUserAccountsTable = table'<UnverifiedUserAccountRecord> "unverified_user_accounts"
    let verificationTokenMappingsTable = table'<VerificationTokenMappingRecord> "verification_token_mappings"
    
    let verificationTokensTable = table'<VerificationTokenRecord> "verification_tokens"
    
    let selectUnVerifiedUserAccountBy (database: UserAccountDb) (mailAddress: string): Async<UnverifiedUserAccountRecord option> =
        async {
            let! result =
                select {
                    for row in unVerifiedUserAccountsTable do
                        where (row.mail_address = mailAddress)
                }
                |> database.connection.SelectAsync<UnverifiedUserAccountRecord>
                |> Async.AwaitTask
            
            return result |> Seq.tryHead
        }
        
    let selectVerifiedUserAccountBy (database: UserAccountDb) (mailAddress: string): Async<VerifiedUserAccountRecord option> =
        async {
            let! result =
                select {
                    for row in verifiedUserAccountsTable do
                        where (row.mail_address = mailAddress)
                }
                |> database.connection.SelectAsync<VerifiedUserAccountRecord>
                |> Async.AwaitTask
            
            return result |> Seq.tryHead
        }
    
    let selectVerificationTokenMappingRecordBy (database: UserAccountDb) (mailAddress: string): Async<VerificationTokenMappingRecord> =
        async {
            let! result =
                select {
                    for row in verificationTokenMappingsTable do
                        where (row.mail_address = mailAddress)
                }
                |> database.connection.SelectAsync<VerificationTokenMappingRecord>
                |> Async.AwaitTask
               
            return result |> Seq.head
        }
        
    let selectVerificationTokenRecordBy (database: UserAccountDb) (token: string): Async<VerificationTokenRecord> =
        async {
            let! result =
                select {
                    for row in verificationTokensTable do
                        where (row.value = token)
                }
                |> database.connection.SelectAsync<VerificationTokenRecord>
                |> Async.AwaitTask
                
            return result |> Seq.head
        }
    let insertIntoVerifiedUserAccountTable (database: UserAccountDb) (verifiedUserAccount: VerifiedUserAccountRecord): Async<unit> =
        async {
            do!
                insert {
                    into verifiedUserAccountsTable
                    value verifiedUserAccount
                }
                |> database.connection.InsertAsync
                |> Async.AwaitTask
                |> Async.Ignore
        }
    let insertIntoUnVerifiedUserAccountTable (database: UserAccountDb) (unVerifiedUserAccount: UnverifiedUserAccountRecord): Async<unit> =
        async {
            do!
                insert {
                    into unVerifiedUserAccountsTable
                    value unVerifiedUserAccount
                }
                |> database.connection.InsertAsync
                |> Async.AwaitTask
                |> Async.Ignore
        }
    let insertIntoVerificationTokenMappingTable (database: UserAccountDb) (verificationTokenMapping: VerificationTokenMappingRecord): Async<unit> =
        async {
           do!
               insert {
                   into verificationTokenMappingsTable
                   value verificationTokenMapping
               }
               |> database.connection.InsertAsync
               |> Async.AwaitTask
               |> Async.Ignore
        }
        
    let insertIntoVerificationTokenTable (database: UserAccountDb) (verificationToken: VerificationTokenRecord): Async<unit> =
        async {
                do!
                    insert {
                        into verificationTokensTable
                        value verificationToken
                    }
                    |> database.connection.InsertAsync
                    |> Async.AwaitTask
                    |> Async.Ignore
        }
        
    let updateVerificationTokenTable (database: UserAccountDb) (verificationTokenRecord: VerificationTokenRecord): Async<unit> =
        async {
            do!
                update {
                    for row in verificationTokensTable do
                        set verificationTokenRecord
                        where (row.value = verificationTokenRecord.value)
                }
                |> database.connection.UpdateAsync
                |> Async.AwaitTask
                |> Async.Ignore
        }