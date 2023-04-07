package edu.northeastern.cs5500.starterbot.service;

import edu.northeastern.cs5500.starterbot.model.EventUserGuild;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.inject.Inject;

/**
 * Work with {@link EventUserGuild} and {@link GenericRepository}, providing services such as adding
 * the user and guild data to the in memory db, and finding the guild id for a user.
 */
public class UserEnterService {

  @Inject
  GenericRepository<EventUserGuild> EventUserGuildRepository;

  @Inject
  public UserEnterService() {
  }

  /**
   * Add the user id from the GuildMemberJoinEvent and the guild id which the user has joined to the
   * in memory db.
   * @param eventUserId the user id from the GuildMemberJoinEvent as a String
   * @param guildId the guild id that the user has joined as a String
   */
  public void mapEventUserGuildId(String eventUserId, String guildId){
    EventUserGuildRepository.add(new EventUserGuild(eventUserId, guildId));
  }
}
