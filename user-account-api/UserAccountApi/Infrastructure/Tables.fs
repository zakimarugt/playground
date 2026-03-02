namespace UserAccountApi.Infrastructure

open System

type UserAccount = {
    id: string
    mailAddress: string
    password: string
}

type UnverifiedUserAccountRecord = {
    mail_address: string
}

type VerifiedUserAccountRecord = {
    id: string
    mail_address: string
    user_account_name: string
    user_account_password: string
}

type VerificationTokenMappingRecord = {
    mail_address: string
    verification_token: string
}

type VerificationTokenRecord = {
    value: string
    expired_date: DateTime
    status: string
}