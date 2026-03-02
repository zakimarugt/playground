package learning.ddd.tweet.post.domain.entity.response

import learning.ddd.tweet.post.domain.valueobject.*

sealed class Response(
    val id: MessageId.ResponseId,
    val responderAccountId: ResponderAccountId,
    var content: Content,
    val targetId: MessageId,
    var likeCount: LikeCount,
    var repostedCount: RepostedCount
) {
    class NewlyCreatedResponse private constructor(
        id: MessageId.ResponseId,
        responderAccountId: ResponderAccountId,
        content: Content,
        targetId: MessageId,
        likeCount: LikeCount,
        repostedCount: RepostedCount
    ): Response(id, responderAccountId, content, targetId, likeCount, repostedCount) {
        companion object {
            fun create(responderAccountId: ResponderAccountId, content: Content, targetId: MessageId): Response {
                return NewlyCreatedResponse(
                    MessageId.ResponseId(),
                    responderAccountId,
                    content,
                    targetId,
                    LikeCount(0),
                    RepostedCount(0)
                )
            }
        }
    }

    class ExistingResponse(
        id: MessageId.ResponseId,
        responderAccountId: ResponderAccountId,
        content: Content,
        targetId: MessageId,
        likeCount: LikeCount,
        repostedCount: RepostedCount
    ): Response(id, responderAccountId, content, targetId, likeCount, repostedCount)
}