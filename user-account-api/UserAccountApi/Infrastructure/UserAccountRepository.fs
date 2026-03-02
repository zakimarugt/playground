namespace UserAccountApi.Infrastructure

open System
open UserAccountApi.Domain
open UserAccountApi.Infrastructure
open UserAccountApi.Infrastructure.UserAccountDb

module UserAccountRepository =
    let save (database: UserAccountDb): UserAccountRepository.save =
        fun (userAccount: UserAccountApi.Domain.UserAccount) ->
            async {
                match userAccount with
                | UnVerified (UnVerifiedUserAccount(mailAddress, verificationToken)) ->
                    let unVerifiedUserAccountRecord = {
                        mail_address = MailAddress.value mailAddress
                    }
                    let verificationTokenMappingRecord = {
                        mail_address = MailAddress.value mailAddress
                        verification_token = VerificationToken.value verificationToken
                    }
                  
                    let verificationTokenRecord =
                        match verificationToken with
                        | VerificationToken.Effective (EffectiveVerificationToken (value, expiredDate)) ->
                            {
                                value = value
                                expired_date = ExpiredDate.value expiredDate
                                status = "effective"
                            }
                        | VerificationToken.UnVerified _ -> failwith "Not implemented."
                     
                    do! insertIntoUnVerifiedUserAccountTable database unVerifiedUserAccountRecord
                    do! insertIntoVerificationTokenMappingTable database verificationTokenMappingRecord
                    do! insertIntoVerificationTokenTable database verificationTokenRecord
                    
                | Verified (VerifiedUserAccount(userAccountId, mailAddress, userAccountName, userAccountPassword)) ->
                    let verifiedUserAccountRecord = {
                         id = UserAccountId.value userAccountId
                         mail_address = MailAddress.value mailAddress
                         user_account_name = UserAccountName.value userAccountName
                         user_account_password = UserAccountPassword.value userAccountPassword
                    }
                
                    let! verificationTokenMappingRecord = selectVerificationTokenMappingRecordBy database (MailAddress.value mailAddress)
                    let! verificationTokenRecord = selectVerificationTokenRecordBy database verificationTokenMappingRecord.verification_token
                    
                    do! insertIntoVerifiedUserAccountTable database verifiedUserAccountRecord    
                    do! updateVerificationTokenTable database  { verificationTokenRecord with status = "ineffective" }
            }
            
    // ドメイン ⇄ DBモデルの変換が読みにくい
    let findBy (database: UserAccountDb) : UserAccountRepository.findBy =
        fun (mailAddress: MailAddress) ->
            async {
                let! verifiedUserAccountRecord = selectVerifiedUserAccountBy database (MailAddress.value mailAddress)
                match verifiedUserAccountRecord with
                | Some record ->
                    return VerifiedUserAccount.from (UserAccountId.from record.id) (mailAddress) (UserAccountName record.user_account_name) (UserAccountPassword record.user_account_password)
                | None ->
                    let! unVerifiedUserAccountRecord = selectUnVerifiedUserAccountBy database (MailAddress.value mailAddress)
                    match unVerifiedUserAccountRecord with
                    | Some record ->
                        let! verificationTokenMappingRecord = selectVerificationTokenMappingRecordBy database (MailAddress.value mailAddress)
                        return UserAccount.UnVerified (UnVerifiedUserAccount (mailAddress, VerificationToken.UnVerified (UnVerifiedVerificationToken verificationTokenMappingRecord.verification_token)))
                    | None -> return failwith "Not implemented."
            }
            