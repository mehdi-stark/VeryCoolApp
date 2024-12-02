package org.techwork.verycool.services.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.SpringBootTest
import org.techwork.verycool.exceptions.UserNotFoundException
import org.techwork.verycool.models.entities.UserEntity
import org.techwork.verycool.repositories.UserRepository
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class UserServiceImplSpec extends Specification {

    UserRepository userRepository = Mock(UserRepository)
    @Subject
    def userService = new UserServiceImpl(userRepository)

    List<UserEntity> userList

    def setup() {
        // Load mocked data from JSON file
        userList = new ObjectMapper().readValue(
                new File("src/test/resources/data/users.json"),
                new TypeReference<List<UserEntity>>() {}
        )
    }

    def "test findUsers - Should return a list of users"() {
        given: "A list of users from the JSON file"
        def users = userList

        when: "The findUsers method is called"
        def result = userService.findAll()

        then: "The list of users should be returned"
        1 * userRepository.findAll() >> users
        result != null
        result.size() == 4
        result[0].fullName == "Alice Smith"
        result[1].fullName == "Bob Johnson"
        result[2].fullName == "Charlie Brown"
        result[3].fullName == "Diana Prince"
    }

    def "test findUserById - Should return a user if found"() {
        given: "A user"
        def user = userList.find { it.id == 1L }

        when: "The findUserById method is called"
        def result = userService.findUserById(1L)

        then: "The user should be returned"
        1 * userRepository.findById(1L) >> Optional.of(user)
        result != null
        result.id == 1L
        result.fullName == "Alice Smith"
    }

    def "test findUserById - Should return null if user not found"() {
        given: "No user found"

        when: "The findUserById method is called"
        userService.findUserById(99L)

        then: "A UserNotFoundException should be thrown"
        1 * userRepository.findById(99L) >> Optional.empty()
        thrown(UserNotFoundException)
    }
}
