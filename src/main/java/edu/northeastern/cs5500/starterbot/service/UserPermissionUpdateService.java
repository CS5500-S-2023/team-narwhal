package edu.northeastern.cs5500.starterbot.service;

import edu.northeastern.cs5500.starterbot.model.EventUserGuild;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;

/**
 * Update user permissions.
 */
@Slf4j
public class UserPermissionUpdateService {

  @Inject
  GenericRepository<EventUserGuild> EventUserGuildRepository;

  @Inject

  public UserPermissionUpdateService() {
  }

  /**
   * Give a member of the server a certain permission in a Discord text channel.
   * @param textChannel the textChannel to give permission to
   * @param member the member to give permission to
   * @param permissionToGive the type of permission to give
   * @return an PermissionOverrideAction to be queued
   */
  public PermissionOverrideAction grantUserPermissionInTextChannel(TextChannel textChannel,
      Member member, Permission permissionToGive) {
    PermissionOverrideAction override = textChannel.upsertPermissionOverride(member);
    return override.grant(permissionToGive);
  }

  // TODO: add for voice channel?

  /**
   * Return the guild id for a user using the user id from the event.
   *
   * @param eventUserID the user id from an event as a String
   * @return the guild id that the user has joined as a String
   */
  @Nonnull
  public String getGuildIdForEventUser(String eventUserID) {
    log.info("Updating permission for user " + eventUserID);
    for (EventUserGuild currentEventUserGuild : EventUserGuildRepository.getAll()) {
      if (eventUserID.equals(currentEventUserGuild.getUserEventId())) {
        return currentEventUserGuild.getGuildId();
      }
    }
    // throw custom user not found exceptions
    // TODO: update exception and add to method signature
    throw new RuntimeException("User not found!");
  }
}
