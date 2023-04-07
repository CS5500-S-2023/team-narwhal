package edu.northeastern.cs5500.starterbot.service;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class UserPrivilegeUpdateService {
  @Inject

  public UserPrivilegeUpdateService() {
  }

  public void updateNewUserPrivilege(@Nonnull SlashCommandInteractionEvent event){
    //event.getGuildChannel();

  }
}
