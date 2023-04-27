package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.model.Membership;
import edu.northeastern.cs5500.starterbot.repository.MembershipRepository;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

/** A class that handles membership flow. */
@Singleton
@IgnoreInGeneratedReport // Can't test; depends on JDA User and Guild
public class MembershipController {

    MembershipRepository membershipRepository;

    @Inject
    public MembershipController(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    /**
     * Gets the user's membership information (user & guild).
     *
     * @param userId - The user we are getting their membership information for.
     * @return the user's membership information.
     */
    public Membership getMembership(String userId) {
        return membershipRepository.get(userId);
    }

    /**
     * Adds the user and guild to the membershipRepository.
     *
     * @param user - The user we are adding to the membershipRepository.
     * @param guild - The guild object attached to the membership we are adding to the
     *     membershipRepository.
     */
    public void addMembership(User user, Guild guild) {
        Membership membership = Membership.builder().user(user).guild(guild).build();
        membershipRepository.add(membership);
    }

    /**
     * Removes membership information for a user.
     *
     * @param userId - The user we are removing from the membershipRepository.
     */
    public void removeMembership(String userId) {
        membershipRepository.delete(userId);
    }
}
