package org.techwork.verycool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=dev")
class VeryCoolApplicationProfileTest {

    @Test
    void applicationStartsWithDevProfile() {
        // Testing that app is starting with profile dev
    }
}
