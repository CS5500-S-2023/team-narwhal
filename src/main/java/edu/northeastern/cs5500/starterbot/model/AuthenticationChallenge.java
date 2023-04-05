package edu.northeastern.cs5500.starterbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class AuthenticationChallenge implements Model {
    ObjectId id;

    // The discord user attempting this challenge
    String discordMemberId;
    // The correct answer to the challenge
    String answer;
    // the number of times that the user has attempted the challenge
    int numAttempts;
    // the time that the challenge is created
    long timeStamp;
    // The current state of the challenge
    AuthenticationState state;

    public AuthenticationChallenge(String discordMemberId, String answer) {
        this.discordMemberId = discordMemberId;
        this.answer = answer;
        numAttempts = 0;
        timeStamp = 0;
        state = AuthenticationState.WAITING_FOR_RESPONSE;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public ObjectId getId() {
        return this.id;
    }
}
