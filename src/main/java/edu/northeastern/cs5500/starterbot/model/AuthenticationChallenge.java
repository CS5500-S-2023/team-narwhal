package edu.northeastern.cs5500.starterbot.model;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public abstract class AuthenticationChallenge implements Model {
    ObjectId id;
    
    // The discord user attempting this challenge
    String discordMemberId;
    // The correct answer to the challenge
    String answer;
    // The current state of the challenge
    AuthenticationState state;
}
