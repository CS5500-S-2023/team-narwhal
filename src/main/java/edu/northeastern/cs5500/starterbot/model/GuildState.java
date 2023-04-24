package edu.northeastern.cs5500.starterbot.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

/** Data model that stores various fields needed for authenticating a user. */
@Data
@Builder
public class GuildState implements Model {
    ObjectId id;

    // The server the bot is setting up
    String guildId;
    // The current state of the guild set up
    @Builder.Default SetupState state = SetupState.UNKNOWN;
}
