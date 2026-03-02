package learning.ddd.tweet.post.interfaceadapter.controller

import learning.ddd.tweet.post.application.usecase.deletepost.DeletePostUsecase
import learning.ddd.tweet.post.application.usecase.deletepost.dto.DeletePostInputDto
import learning.ddd.tweet.post.application.usecase.editposts.EditPostUsecase
import learning.ddd.tweet.post.application.usecase.editposts.dto.EditPostInputDto
import learning.ddd.tweet.post.application.usecase.editposts.dto.EditPostRequest
import learning.ddd.tweet.post.application.usecase.findFolloweePosts.FindFolloweePostsUsecase
import learning.ddd.tweet.post.application.usecase.findposts.FindUserAccountPostsUsecase
import learning.ddd.tweet.post.application.usecase.findresponses.FindResponsesUsecase
import learning.ddd.tweet.post.application.usecase.findresponses.dto.FindResponsesInputDto
import learning.ddd.tweet.post.application.usecase.registerposts.RegisterPostsUseCase
import learning.ddd.tweet.post.application.usecase.registerposts.dto.RegisterPostsInputDto
import learning.ddd.tweet.post.application.usecase.registerposts.dto.RegisterPostsRequest
import learning.ddd.tweet.post.application.usecase.respondToPost.RespondToPostUsecase
import learning.ddd.tweet.post.application.usecase.respondToPost.dto.RespondToPostInputDto
import learning.ddd.tweet.post.application.usecase.respondToPost.dto.RespondToPostRequest
import learning.ddd.tweet.post.domain.entity.response.ResponderAccountId
import learning.ddd.tweet.post.domain.valueobject.Content
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import learning.ddd.tweet.post.interfaceadapter.conversion.json.converter.PostsJsonConverter
import learning.ddd.tweet.post.interfaceadapter.conversion.json.converter.ResponsesJsonConverter
import learning.ddd.tweet.post.interfaceadapter.conversion.json.model.PostsJson
import learning.ddd.tweet.post.interfaceadapter.conversion.json.model.ResponsesJson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PostController(
    private val findUserAccountPostsUseCase: FindUserAccountPostsUsecase,
    private val findFolloweePostsUseCase: FindFolloweePostsUsecase,
    private val findResponsesUseCase: FindResponsesUsecase,
    private val registerPostsUseCase: RegisterPostsUseCase,
    private val editPostUseCase: EditPostUsecase,
    private val deletePostUseCase: DeletePostUsecase,
    private val respondToPostUseCase: RespondToPostUsecase) {

    @GetMapping("/userAccounts/{userAccountId}/posts")
    suspend fun findUserAccountPosts(@PathVariable("userAccountId") userAccountId: String): ResponseEntity<PostsJson> {
        val posts = findUserAccountPostsUseCase.execute(UserAccountId(userAccountId))

        return ResponseEntity.ok(PostsJsonConverter.toJson(posts))
    }

    @CrossOrigin
    @PostMapping("/userAccounts/{userAccountId}/posts")
    fun savePost(@RequestBody request: RegisterPostsRequest, @PathVariable("userAccountId") userAccountId: String): ResponseEntity<String> {
        return Content.validate(request.content).fold(
            {
                UserAccountId.validate(userAccountId).fold(
                    {
                        registerPostsUseCase.execute(RegisterPostsInputDto(userAccountId, request.content))
                        ResponseEntity.status(HttpStatus.CREATED).build()
                    },
                    { ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message) }
                )
            },
            { ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message) }
        )
    }

    @PatchMapping("/userAccounts/{userAccountId}/posts/{postId}")
    fun editPost(
        @RequestBody request: EditPostRequest,
        @PathVariable("userAccountId") userAccountId: String,
        @PathVariable("postId") postId: String): ResponseEntity<Nothing> {
        editPostUseCase.execute(EditPostInputDto(userAccountId, postId, request.content))

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/userAccounts/{userAccountId}/posts/{postId}")
    suspend fun deletePost(
        @PathVariable userAccountId: String,
        @PathVariable postId: String): ResponseEntity<Unit> {
        deletePostUseCase.execute(DeletePostInputDto(UserAccountId(userAccountId), MessageId.PostId(postId)))

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/userAccounts/{userAccountId}/posts/{postId}/responses")
    fun registerResponse(
        @PathVariable("userAccountId") userAccountId: String,
        @PathVariable("postId") postId: String,
        @RequestBody request: RespondToPostRequest): ResponseEntity<Nothing>   {
        respondToPostUseCase.execute(
            RespondToPostInputDto(
                ResponderAccountId(request.responderAccountId),
                Content(request.responseContent),
                UserAccountId(userAccountId),
                MessageId.PostId(postId)
            )
        )

        return ResponseEntity.status(201).build()
    }

    @GetMapping("/userAccounts/{userAccountId}/followees/posts")
    fun findFolloweePosts(@PathVariable("userAccountId") userAccountId: String): ResponseEntity<PostsJson> {
        val followeePosts = findFolloweePostsUseCase.execute(UserAccountId(userAccountId))

        return ResponseEntity.ok(PostsJsonConverter.toJson(followeePosts))
    }

    @GetMapping("/userAccounts/{userAccountId}/posts/{postId}/responses")
    fun findResponses(@PathVariable("userAccountId") userAccountId: String, @PathVariable("postId") postId: String): ResponseEntity<ResponsesJson> {
        val responses = findResponsesUseCase.execute(FindResponsesInputDto(userAccountId, postId))

        return ResponseEntity.ok(ResponsesJsonConverter.toJson(MessageId.PostId(postId), responses))
    }
}
