package org.techwork.verycool.controllers

import org.springframework.test.web.servlet.MockMvc
import org.techwork.verycool.constants.Constants
import org.techwork.verycool.models.entities.UserEntity
import org.techwork.verycool.services.UserService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class UserControllerSpec extends Specification {

    MockMvc mockMvc
    UserService userService

    def setup() {
        userService = Mock(UserService)
        mockMvc = standaloneSetup(new UserController(userService)).build()
    }

    def "test findUsers - Should return a list of users"() {
        given: "A list of users"
        UserEntity user1 = new UserEntity(id: 1L, username: "User 1")
        UserEntity user2 = new UserEntity(id: 2L, username: "User 2")
        userService.findUsers() >> [user1, user2]

        when: "The findUsers endpoint is called"
        def result = mockMvc.perform(get(Constants.URL_BASE + "/users"))

        then: "The response contains the list of users"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].id').value(1L))
                .andExpect(jsonPath('$[0].username').value("User 1"))
                .andExpect(jsonPath('$[1].id').value(2L))
                .andExpect(jsonPath('$[1].username').value("User 2"))
    }

    def "test findUserById - Should return a user by ID"() {
        given: "A user with a specific ID"
        Long userId = 1L
        UserEntity user = new UserEntity(id: userId, username: "User 1")
        userService.findUserById(userId) >> user

        when: "The findUserById endpoint is called"
        def result = mockMvc.perform(get(Constants.URL_BASE + "/user/{userId}", userId))

        then: "The response contains the user"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(userId))
                .andExpect(jsonPath('$.username').value("User 1"))
    }

    def "test findUserById - Should return no content if user not found"() {
        given: "No user with the specified ID"
        Long userId = 1L
        userService.findUserById(userId) >> null

        when: "The findUserById endpoint is called"
        def result = mockMvc.perform(get(Constants.URL_BASE + "/user/{userId}", userId))

        then: "The response should indicate no content"
        result.andExpect(status().isNoContent())
    }
}
