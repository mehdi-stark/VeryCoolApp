package org.techwork.verycool.controllers

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.techwork.verycool.constants.Constants
import org.techwork.verycool.models.entities.CommentEntity
import org.techwork.verycool.services.CommentService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class CommentsControllerSpec extends Specification {

    MockMvc mockMvc
    CommentService commentService

    def setup() {
        commentService = Mock(CommentService)
        mockMvc = standaloneSetup(new CommentsController(commentService)).build()
    }

    def "test getComments - Should return a list of comments"() {
        given: "A list of comments"
        CommentEntity comment1 = new CommentEntity(id: 1L, content: "Comment 1")
        CommentEntity comment2 = new CommentEntity(id: 2L, content: "Comment 2")
        commentService.findAll() >> [comment1, comment2]

        when: "The getComments endpoint is called"
        def result = mockMvc.perform(get(Constants.URL_BASE + "/comments"))

        then: "The response contains the list of comments"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].id').value(1L))
                .andExpect(jsonPath('$[0].content').value("Comment 1"))
                .andExpect(jsonPath('$[1].id').value(2L))
                .andExpect(jsonPath('$[1].content').value("Comment 2"))
    }

    def "test getUserComments - Should return user comments"() {
        given: "A list of user comments"
        Long userId = 1L
        CommentEntity comment1 = new CommentEntity(id: 1L, content: "User Comment 1")
        commentService.findUserComment(userId) >> [comment1]

        when: "The getUserComments endpoint is called"
        def result = mockMvc.perform(get(Constants.URL_BASE + "/comments/user/{userId}", userId))

        then: "The response contains the user's comments"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].id').value(1L))
                .andExpect(jsonPath('$[0].content').value("User Comment 1"))
    }

    def "test saveComment - Should return saved comment"() {
        given: "A new comment"
        CommentEntity comment = new CommentEntity(content: "New Comment", id: 1L)
        commentService.saveComment(_ as CommentEntity) >> comment

        when: "The saveComment endpoint is called"
        def result = mockMvc.perform(post(Constants.URL_BASE + "/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"content": "New Comment"}'))

        then: "The comment is saved and returned"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.content').value("New Comment"))
                .andExpect(jsonPath('$.id').value(1))
    }

    def "test deleteComment - Should delete comment"() {
        given: "An existing comment"
        CommentEntity comment = new CommentEntity(id: 1L, content: "Comment to Delete")
        commentService.deleteComment(comment) >> {}

        when: "The deleteComment endpoint is called"
        def result = mockMvc.perform(delete(Constants.URL_BASE + "/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"id": 1, "content": "Comment to Delete"}'))

        then: "The response should confirm deletion"
        result.andExpect(status().isOk())
    }
}
