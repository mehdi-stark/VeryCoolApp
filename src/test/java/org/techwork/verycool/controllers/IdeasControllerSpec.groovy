package org.techwork.verycool.controllers

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.techwork.verycool.constants.Constants
import org.techwork.verycool.models.entities.IdeaEntity
import org.techwork.verycool.services.IdeaService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class IdeasControllerSpec extends Specification {

    MockMvc mockMvc
    IdeaService ideaService

    def setup() {
        ideaService = Mock(IdeaService)
        mockMvc = standaloneSetup(new IdeasController(ideaService)).build()
    }

    def "test findIdeas - Should return a list of ideas"() {
        given: "A list of ideas"
        IdeaEntity idea1 = new IdeaEntity(id: 1L, title: "Idea 1")
        IdeaEntity idea2 = new IdeaEntity(id: 2L, title: "Idea 2")
        ideaService.findIdeas() >> [idea1, idea2]

        when: "The findIdeas endpoint is called"
        def result = mockMvc.perform(get(Constants.URL_BASE + "/ideas"))

        then: "The response contains the list of ideas"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].id').value(1L))
                .andExpect(jsonPath('$[0].title').value("Idea 1"))
                .andExpect(jsonPath('$[1].id').value(2L))
                .andExpect(jsonPath('$[1].title').value("Idea 2"))
    }

    def "test findUserIdeas - Should return user ideas"() {
        given: "A list of user ideas"
        Long userId = 1L
        IdeaEntity idea1 = new IdeaEntity(id: 1L, title: "User Idea 1")
        ideaService.findUserIdeas(userId) >> [idea1]

        when: "The findUserIdeas endpoint is called"
        def result = mockMvc.perform(get(Constants.URL_BASE + "/ideas/user/{userId}", userId))

        then: "The response contains the user's ideas"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].id').value(1L))
                .andExpect(jsonPath('$[0].title').value("User Idea 1"))
    }

    def "test saveIdea - Should return saved idea"() {
        given: "A new idea"
        IdeaEntity idea = new IdeaEntity(title: "New Idea", id: 1L)
        ideaService.saveIdea(_ as IdeaEntity) >> idea

        when: "The saveIdea endpoint is called"
        def result = mockMvc.perform(post(Constants.URL_BASE + "/idea")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"title": "New Idea"}'))

        then: "The idea is saved and returned"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.title').value("New Idea"))
                .andExpect(jsonPath('$.id').value(1))
    }

    def "test likeIdea - Should add like to idea"() {
        given: "An existing idea"
        IdeaEntity idea = new IdeaEntity(id: 1L, title: "Idea to Like")
        ideaService.like(1L) >> idea

        when: "The likeIdea endpoint is called"
        def result = mockMvc.perform(patch(Constants.URL_BASE + "/idea/{ideaId}/like", 1L))

        then: "The response should contain the updated idea"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(1L))
                .andExpect(jsonPath('$.title').value("Idea to Like"))
    }

    def "test unlikeIdea - Should remove like from idea"() {
        given: "An existing idea"
        IdeaEntity idea = new IdeaEntity(id: 1L, title: "Idea to Unlike")
        ideaService.unlike(1L) >> idea

        when: "The unlikeIdea endpoint is called"
        def result = mockMvc.perform(patch(Constants.URL_BASE + "/idea/{ideaId}/unlike", 1L))

        then: "The response should contain the updated idea"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(1L))
                .andExpect(jsonPath('$.title').value("Idea to Unlike"))
    }

    def "test deleteIdea - Should delete idea"() {
        given: "An existing idea"
        IdeaEntity idea = new IdeaEntity(id: 1L, title: "Idea to Delete")
        ideaService.deleteIdea(idea) >> {}

        when: "The deleteIdea endpoint is called"
        def result = mockMvc.perform(delete(Constants.URL_BASE + "/idea")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"id": 1, "title": "Idea to Delete"}'))

        then: "The response should confirm deletion"
        result.andExpect(status().isOk())
                .andExpect(content().string("Idea has been deleted successfully"))
    }
}
