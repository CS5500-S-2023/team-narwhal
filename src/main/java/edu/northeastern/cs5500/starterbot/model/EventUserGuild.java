package edu.northeastern.cs5500.starterbot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * Data model that stores (creates mapping of) event user IDs and the guild IDs.
 */
@Data
@AllArgsConstructor
public class EventUserGuild implements Model{
  ObjectId id;
  String userEventId;
  String guildId;

  /**
   * Constructor without the ObjectId.
   * @param userEventId the event userId as a String
   * @param guildId the guild id as a String
   */
  public EventUserGuild(String userEventId, String guildId) {
    this.userEventId = userEventId;
    this.guildId = guildId;
  }
}
