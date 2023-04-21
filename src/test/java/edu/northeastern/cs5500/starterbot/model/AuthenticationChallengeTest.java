package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link AuthenticationChallenge}, omitted builder and data methods with Lombok;
 * only testing methods from the parent interface {@link Model}.
 */
class AuthenticationChallengeTest {

    private AuthenticationChallenge authenticationChallenge;

    @BeforeEach
    void setUp() {
        authenticationChallenge =
                AuthenticationChallenge.builder()
                        .id(new ObjectId("666f6f2d6261722d71757578"))
                        .build();
    }

    @Test
    void getId() {
        assertThat(authenticationChallenge.getId())
                .isEqualTo(new ObjectId("666f6f2d6261722d71757578"));
    }

    @Test
    void setId() {
        authenticationChallenge.setId(new ObjectId("666f6f2d6261722d71757000"));
        assertThat(authenticationChallenge.getId())
                .isEqualTo(new ObjectId("666f6f2d6261722d71757000"));
    }
}
