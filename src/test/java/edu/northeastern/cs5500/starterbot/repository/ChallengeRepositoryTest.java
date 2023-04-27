package edu.northeastern.cs5500.starterbot.repository;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChallengeRepositoryTest {
    private AuthenticationChallenge testChallenge1;
    private AuthenticationChallenge testChallenge2;
    private ObjectId testId1;
    private ObjectId testId2;
    private ChallengeRepository testRepo;

    @BeforeEach
    void setUp() {
        testId1 = new ObjectId("666f6f2d6261722d71757578");
        testId2 = new ObjectId("666f6f2d6261722d71757000");
        testChallenge1 =
                AuthenticationChallenge.builder()
                        .id(testId1)
                        .eventUserId("event1")
                        .guildId("guild1")
                        .answer("correct")
                        .build();
        testChallenge2 =
                AuthenticationChallenge.builder()
                        .id(testId2)
                        .eventUserId("event2")
                        .guildId("guild2")
                        .answer("wrong")
                        .build();
        testRepo = new ChallengeRepository();
        testRepo.add(testChallenge1);
    }

    @Test
    void testGetByObjectId() {
        assertThat(testRepo.get(testId1)).isEqualTo(testChallenge1);
    }

    @Test
    void testGetByEventUserId() {
        assertThat(testRepo.get("event1")).isEqualTo(testChallenge1);
    }

    @Test
    void add() {
        assertThat(testRepo.getAll().size()).isEqualTo(1);
        testRepo.add(testChallenge2);
        assertThat(testRepo.getAll().size()).isEqualTo(2);
    }

    @Test
    void update() {}

    @Test
    void delete() {
        assertThat(testRepo.getAll().size()).isEqualTo(1);
        testRepo.delete(testId1);
        assertThat(testRepo.getAll().size()).isEqualTo(0);
    }

    @Test
    void getAll() {
        assertThat(testRepo.getAll()).containsExactly(testChallenge1);
    }

    @Test
    void count() {
        assertThat(testRepo.count()).isEqualTo(1);
    }
}
