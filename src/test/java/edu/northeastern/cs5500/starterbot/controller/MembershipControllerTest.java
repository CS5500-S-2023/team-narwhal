package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.repository.MembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MembershipControllerTest {
    private MembershipRepository membershipRepository;
    private MembershipController membershipController;

    @BeforeEach
    void setUp() {
        membershipRepository = new MembershipRepository();
        membershipController = new MembershipController(membershipRepository);
    }

    @Test
    void testAddMembership() {
        // TODO: pass in the user id and the guild id?
        // membershipController.addMembership();

    }

    @Test
    void testGetMembership() {}

    @Test
    void testRemoveMembership() {}
}
