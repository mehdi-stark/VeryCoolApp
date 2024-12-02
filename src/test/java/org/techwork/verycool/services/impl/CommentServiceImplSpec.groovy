package org.techwork.verycool.services.impl

import org.techwork.verycool.models.entities.CommentEntity
import org.techwork.verycool.repositories.CommentRepository
import spock.lang.Specification
import spock.lang.Subject

class CommentServiceImplSpec extends Specification {

    def commentRepository = Mock(CommentRepository)

    @Subject
    def commentService = new CommentServiceImpl(commentRepository)

    def "should find all comments"() {
        given: "a list of comments in the repository"
        def comments = [new CommentEntity(content: "Comment 1"), new CommentEntity(content: "Comment 2")]
        commentRepository.findAll() >> comments

        when: "retrieving all comments"
        def result = commentService.findAll()

        then: "all comments are returned"
        result.size() == 2
        result*.content == ["Comment 1", "Comment 2"]
    }

    def "should find one comment by id"() {
        given: "a comment exists in the repository"
        def comment = new CommentEntity(content: "Specific Comment")
        commentRepository.findById(1L) >> Optional.of(comment)

        when: "retrieving a comment by ID"
        def result = commentService.findOne(1L)

        then: "the correct comment is returned"
        result.content == "Specific Comment"
    }

    def "should return null if comment not found by id"() {
        given: "no comment exists for the given ID"
        commentRepository.findById(1L) >> Optional.empty()

        when: "retrieving a comment by ID"
        def result = commentService.findOne(1L)

        then: "null is returned"
        result == null
    }

    def "should find comments for a specific user"() {
        given: "a user with comments in the repository"
        def userId = 1L
        def userComments = [new CommentEntity(content: "User Comment 1"), new CommentEntity(content: "User Comment 2")]
        commentRepository.findAllByUserId(userId) >> userComments

        when: "retrieving comments for the user"
        def result = commentService.findUserComment(userId)

        then: "the user's comments are returned"
        result.size() == 2
        result*.content == ["User Comment 1", "User Comment 2"]
    }

    def "should save a new comment"() {
        given: "a comment to save"
        def newComment = new CommentEntity(content: "New Comment")
        commentRepository.save(newComment) >> newComment

        when: "saving the comment"
        def result = commentService.saveComment(newComment)

        then: "the comment is saved and returned"
        result.content == "New Comment"
    }

    def "should delete an existing comment"() {
        given: "an existing comment"
        def existingComment = new CommentEntity(content: "Comment to Delete")

        when: "deleting the comment"
        commentService.deleteComment(existingComment)

        then: "the repository delete method is called"
        1 * commentRepository.delete(existingComment)
    }
}
