package edu.northeastern.cs5500.starterbot.handler.join;

import edu.northeastern.cs5500.starterbot.controller.MembershipController;
import edu.northeastern.cs5500.starterbot.controller.RoleController;
import edu.northeastern.cs5500.starterbot.repository.MembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WelcomeJoinHandlerTest {
    private WelcomeJoinHandler welcomeJoinHandler;
    private MembershipController membershipController;
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        membershipController = new MembershipController(new MembershipRepository());
        // roleController = new RoleController();
    }

    @Test
    void testGenerateWelcomeMsg() {}

    @Test
    void testHandleGuildMemberJoinEvent() {}
}
