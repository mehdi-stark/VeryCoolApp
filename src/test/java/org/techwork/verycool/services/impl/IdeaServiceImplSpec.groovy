package org.techwork.verycool.services.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.techwork.verycool.exceptions.IdeaNotFoundException
import org.techwork.verycool.models.entities.IdeaEntity
import org.techwork.verycool.repositories.IdeaRepository
import spock.lang.Specification
import spock.lang.Subject

@Slf4j
class IdeaServiceImplSpec extends Specification {

    IdeaRepository ideaRepository  = Mock(IdeaRepository)

    @Subject
    def ideaService = new IdeaServiceImpl(ideaRepository)

    List<IdeaEntity> ideaList

    def setup() {
        // Load mocked data from JSON file
        ideaList = new ObjectMapper().readValue(
                new File("src/test/resources/data/ideas.json"),
                new TypeReference<List<IdeaEntity>>() {}
        )
    }

    def "test findIdeas - Should return all ideas"() {
        given: "A list of ideas from the JSON file"
        ideaRepository.findAll() >> ideaList

        when: "The findIdeas method is called"
        def result = ideaService.findIdeas()

        then: "It should return the list of ideas"
        result.size() == ideaList.size()
        result[0].title == ideaList[0].title
        result[0].description == ideaList[0].description
        result[1].title == ideaList[1].title
        result[1].description == ideaList[1].description
    }

    def "test findOne - Should return an idea by id"() {
        given: "An idea exists in the list"
        def idea = ideaList.find { it.id == 1L }
        ideaRepository.findById(1L) >> Optional.of(idea)

        when: "The findOne method is called with an existing ID"
        def result = ideaService.findOne(1L)

        then: "It should return the idea"
        result.title == idea.title
        result.description == idea.description
        result.nbLikes == idea.nbLikes
    }

    def "test findOne - Should throw IdeaNotFoundException if idea not found"() {
        given: "No idea exists with the given id"
        ideaRepository.findById(99L) >> Optional.empty()

        when: "The findOne method is called with a non-existing ID"
        ideaService.findOne(99L)

        then: "It should throw IdeaNotFoundException"
        thrown(IdeaNotFoundException)
    }

    def "test findUserIdeas - Should return ideas of a user"() {
        given: "A userId and the ideas from the JSON file"
        def userId = 1L
        def userIdeas = ideaList.findAll { it.user.id == userId }
        ideaRepository.findByUserId(userId) >> userIdeas

        when: "The findUserIdeas method is called with the user's ID"
        def result = ideaService.findUserIdeas(userId)

        then: "It should return the ideas for that user"
        result.size() == userIdeas.size()
        result.every { it.user.id == userId }
    }


    def "test save - Should save an idea"() {
        given: "An idea"
        def newIdea = new IdeaEntity(id: 2L, title: "Test Idea", nbLikes: 0)

        when: "The save method is called"
        println("Going to when")
        ideaService.saveIdea(newIdea)

        then: "The idea should be saved and returned"
        1 * ideaRepository.save(newIdea)
    }

    def "test deleteIdea - Should delete an idea"() {
        given: "An existing idea"
        def ideaToDelete = ideaList.find { it.id == 1L }
        ideaRepository.findById(1L) >> Optional.of(ideaToDelete)

        when: "The deleteIdea method is called"
        ideaService.deleteIdea(ideaToDelete)

        then: "It should delete the idea"
        1 * ideaRepository.delete(ideaToDelete)
    }

    def "test like - Should increase the like count of an idea"() {
        given: "An idea with no likes"
        def idea = ideaList.find { it.id == 1L }
        ideaRepository.findById(1L) >> Optional.of(idea)

        when: "The like method is called"
        ideaService.like(1L)

        then: "The like count of the idea should increase"
        1 * ideaRepository.save(idea)
        idea.nbLikes == 1
    }

    def "test unlike - Should decrease the like count of an idea"() {
        given: "An idea with one like"
        def idea = new IdeaEntity(id: 1L, nbLikes: 1)
        ideaRepository.findById(1L) >> Optional.of(idea)

        when: "The unlike method is called"
        ideaService.unlike(1L)

        then: "The like count of the idea should decrease"
        1 * ideaRepository.save(idea)
        idea.nbLikes == 0
    }
}
