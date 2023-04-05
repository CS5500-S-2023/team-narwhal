package edu.northeastern.cs5500.starterbot.service;

import edu.northeastern.cs5500.starterbot.model.UserGuild;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.inject.Inject;

public class UserEnterService {

  @Inject
  GenericRepository<UserGuild> userGuildRepository;

  @Inject
  public UserEnterService() {
  }

  public void mapUserGuild(String userId, String guildMemberId){
    UserGuild userGuild = new UserGuild(userId, guildMemberId);
    userGuildRepository.add(userGuild);
  }

  @Nonnull
  public String getGuildIdForEventUser(String eventUserID) {
    for (UserGuild userGuild : userGuildRepository.getAll()) {
      if (userGuild.getUserEventId().equals(eventUserID)) {
        return userGuild.getGuildMemberId();
      }
    }
    // throw custom user not found exceptions
    throw new RuntimeException("User not found!");
  }
}
