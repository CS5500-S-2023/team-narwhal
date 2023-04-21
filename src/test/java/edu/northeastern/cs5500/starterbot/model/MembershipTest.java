package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Membership}, omitted builder and data methods with Lombok; only testing
 * methods from the parent interface {@link Model}.
 */
class MembershipTest {

    private Membership membership;

    @BeforeEach
    void setUp() {
        membership = Membership.builder().id(new ObjectId("666f6f2d6261722d71757578")).build();
    }

    @Test
    void getId() {
        assertThat(membership.getId()).isEqualTo(new ObjectId("666f6f2d6261722d71757578"));
    }

    @Test
    void setId() {
        membership.setId(new ObjectId("666f6f2d6261722d71757000"));
        assertThat(membership.getId()).isEqualTo(new ObjectId("666f6f2d6261722d71757000"));
    }
}
