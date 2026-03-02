namespace UserAccountApi.Domain

open System
open System.Text.RegularExpressions

type UserAccountId = UserAccountId of Guid

type UserAccountName = UserAccountName of string
type UserAccountPassword = UserAccountPassword of string
type MailAddress = MailAddress of string

type ExpiredDate = ExpiredDate of DateTime

type EffectiveVerificationToken = EffectiveVerificationToken of string * ExpiredDate
type InEffectiveVerificationToken = InEffectiveVerificationToken of string * ExpiredDate
type UnVerifiedVerificationToken = UnVerifiedVerificationToken of string
type VerificationToken =
    | Effective of EffectiveVerificationToken
    // | InEffective of InEffectiveVerificationToken
    | UnVerified of UnVerifiedVerificationToken
        
type VerifiedUserAccount = VerifiedUserAccount of userAccountId: UserAccountId * mailAddress: MailAddress * userAccountName: UserAccountName * userAccountPassword: UserAccountPassword
type UnVerifiedUserAccount = UnVerifiedUserAccount of mailAddress: MailAddress * verificationToken: VerificationToken
type UserAccount = 
    | Verified of VerifiedUserAccount
    | UnVerified of UnVerifiedUserAccount

module UserAccountId =
    let value (UserAccountId userAccountId) = userAccountId.ToString()
    
    let from (value: string) = UserAccountId (Guid.Parse value)
    
    let create () = UserAccountId (Guid.NewGuid())

module UserAccountName =
    let value (UserAccountName userAccountName) = userAccountName
    
    let validate (value: string) =
        if value.Length = 0 || value.Length < 20
        then Ok ()
        else Error "The value should be less than 20 characters."
    
module MailAddress =
    let value (MailAddress mailAddress) = mailAddress
    // メールアドレスのドメインのルールは後で調べる
    let validate (value: string): Result<unit, string> =
        if Regex.IsMatch(value,"^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}$")
        then Ok ()
        else Error "The value does not match a format of email address."

module ExpiredDate =
    let value (ExpiredDate expiredDate) = expiredDate
module VerificationToken =
    let value (verificationToken: VerificationToken) =
        match verificationToken with
        | Effective (EffectiveVerificationToken(value, _)) -> value
        // | InEffective (InEffectiveVerificationToken(value, _)) -> value
        | VerificationToken.UnVerified (UnVerifiedVerificationToken(value)) -> value

    let create (): EffectiveVerificationToken = EffectiveVerificationToken (Guid.NewGuid().ToString(), ExpiredDate DateTime.Now)
module UserAccountPassword =
    let value (UserAccountPassword userAccountPassword) = userAccountPassword
    let validate (value: string): Result<unit, string> =
        if String.length value >= 8
        then Ok ()
        else Error "The value should be more than 8 characters"

module VerifiedUserAccount =
    let from (userAccountId: UserAccountId) (mailAddress: MailAddress) (userAccountName: UserAccountName) (userAccountPassword: UserAccountPassword) =
         UserAccount.Verified (VerifiedUserAccount (userAccountId, mailAddress, userAccountName, userAccountPassword))
         
module UserAccount =
    let createUnVerifiedUserAccount (mailAddress: MailAddress) : UnVerifiedUserAccount =
        UnVerifiedUserAccount (mailAddress, Effective (VerificationToken.create()))
        
    let createVerifiedUserAccount (mailAddress: MailAddress) (userAccountName: UserAccountName) (userAccountPassword: UserAccountPassword) =
        VerifiedUserAccount.from (UserAccountId.create()) mailAddress userAccountName userAccountPassword
        
    let mailAddress (userAccount: UserAccount) =
        match userAccount with
        | Verified (VerifiedUserAccount(_, mailAddress, _, _)) -> mailAddress
        | UnVerified (UnVerifiedUserAccount (mailAddress, _)) -> mailAddress
        
    let verify (existingUnVerifiedUserAccount: UserAccount)
               (unVerifiedUserAccount: UnVerifiedUserAccount) =
        match existingUnVerifiedUserAccount with
        | Verified _ -> Ok()
        | UnVerified compared ->
            if compared = unVerifiedUserAccount
            then Ok()
            else Error()