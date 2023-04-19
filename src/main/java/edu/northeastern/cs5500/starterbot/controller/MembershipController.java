package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Membership;
import edu.northeastern.cs5500.starterbot.repository.MembershipRepository;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

@Singleton
public class MembershipController {

    MembershipRepository membershipRepository;

    @Inject
    public MembershipController(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Membership getMembership(String userId) {
        return membershipRepository.get(userId);
    }

    public void addMembership(User user, Guild guild) {
        Membership membership = Membership.builder().user(user).guild(guild).build();
        membershipRepository.add(membership);
    }

    public void removeMembership(String userId) {
        membershipRepository.delete(userId);
    }
}
