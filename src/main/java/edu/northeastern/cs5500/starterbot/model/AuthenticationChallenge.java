package edu.northeastern.cs5500.starterbot.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

/** Data model that stores various fields needed for authenticating a user. */
@Data
@Builder
public class AuthenticationChallenge implements Model {
    ObjectId id;

    // The discord user attempting this challenge, currently using event userId not memberId
    String eventUserId;
    // The server the user is authenticating for
    String guildId;
    // The correct answer to the challenge
    String answer;
    // the number of times that the user has attempted the challenge
    @Builder.Default int numAttempts = 0;
    // the time that the challenge is created
    @Builder.Default Date timeStamp = new Date();
    // The current state of the challenge
    @Builder.Default AuthenticationState state = AuthenticationState.UNKNOWN;
}
