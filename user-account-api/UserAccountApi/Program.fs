namespace UserAccountApi

#nowarn "20"
open System
open System.Threading.Tasks
open Microsoft.AspNetCore.Builder
open Microsoft.AspNetCore.Http
open Microsoft.Extensions.Configuration
open Microsoft.Extensions.DependencyInjection
open MySqlConnector

open UserAccountApi.Application
open UserAccountApi.Application.CreateUserAccountUseCase
open UserAccountApi.Infrastructure
open UserAccountApi.InterfaceAdapter
open UserAccountApi.InterfaceAdapter.CreateUserAccountHandler
open UserAccountApi.InterfaceAdapter.CreateVerifiedUserAccountHandler

type PingResponseJson = {
    Message: string
}

module Program =
    let exitCode = 0

    [<EntryPoint>]
    let main args =

        let builder = WebApplication.CreateBuilder(args)
        
        builder.Services.AddTransient<MySqlConnection>(fun _ ->
            new MySqlConnection(builder.Configuration.GetConnectionString("Default"))
        )
        |> ignore
        
        builder.Services.AddTransient<UserAccountDb>() |> ignore
        
        Dapper.FSharp.MySQL.OptionTypes.register()
        
        let app = builder.Build()
        
        app.MapGet("/v1/systems/ping", Func<IResult>(fun () ->
            Results.Ok { Message = "pong" }
            )
        )
        |> ignore
        
        app.MapPost(
            "/v1/unVerifiedUserAccounts",
            Func<UserAccountDb, CreateUnVerifiedUserAccountRequestJson, Task<IResult>>(fun db requestJson ->
                async {
                    let dependencies: Dependencies = {
                        save = UserAccountRepository.save db
                    }
                    return! CreateUserAccountHandler.handle dependencies requestJson
                }
                |> Async.StartAsTask
            )
        )
        |> ignore
        
        app.MapPost(
            "/v1/verifiedUserAccounts",
            Func<UserAccountDb, CreateVerifiedUserAccountRequestJson, Task<IResult>>(fun db requestJson ->
                async {
                    let dependencies: CreateVerifiedUserAccountUseCase.Dependencies = {
                        save = UserAccountRepository.save db
                        findBy = UserAccountRepository.findBy db
                    }
                    
                    return! CreateVerifiedUserAccountHandler.handle dependencies requestJson
                }
                |> Async.StartAsTask
            )
         )
        |> ignore

        app.Run()
        
        exitCode