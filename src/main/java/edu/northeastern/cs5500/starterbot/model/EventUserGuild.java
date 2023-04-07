package edu.northeastern.cs5500.starterbot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class EventUserGuild implements Model{
  ObjectId id;
  String userEventId;
  String guildId;

  public EventUserGuild(String userEventId, String guildId) {
    this.userEventId = userEventId;
    this.guildId = guildId;
  }
}
