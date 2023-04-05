package edu.northeastern.cs5500.starterbot.model;

import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;

@Singleton
public class GuildStore {
  @Getter
  @Setter
  Guild guild;
  @Inject
  public GuildStore() {}
}
