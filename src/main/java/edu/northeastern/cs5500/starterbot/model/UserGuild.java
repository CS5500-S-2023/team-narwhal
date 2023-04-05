package edu.northeastern.cs5500.starterbot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class UserGuild implements Model{
  ObjectId id;
  String userEventId;
  String guildMemberId;

  public UserGuild(String userEventId, String guildMemberId) {
    this.userEventId = userEventId;
    this.guildMemberId = guildMemberId;
  }

  @Override
  public ObjectId getId() {
    return this.id;
  }

  @Override
  public void setId(ObjectId id) {
    this.id = id;

  }
}
