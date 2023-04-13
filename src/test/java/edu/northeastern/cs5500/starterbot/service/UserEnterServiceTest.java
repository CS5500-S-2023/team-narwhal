package edu.northeastern.cs5500.starterbot.service;

import static org.junit.jupiter.api.Assertions.*;

import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserEnterServiceTest {

    private UserEnterService userEnterService;

    @BeforeEach
    void setUp() {
        userEnterService = new UserEnterService(new InMemoryRepository<>());
    }

    @Test
    void mapEventUserGuildId() {
        userEnterService.mapEventUserGuildId("testEventUser1", "testGuild1");
        assertEquals(1, userEnterService.eventUserGuildRepository.count());
    }
}
