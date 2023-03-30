package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.authentication.NoAuthenticationSessionException;
import edu.northeastern.cs5500.starterbot.authentication.TooManyAttemptsException;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;

public interface Authenticator {
    AuthenticationChallenge begin(String discordMemberId);
    boolean verify(String discordMemberId, String inputCode) throws TooManyAttemptsException, NoAuthenticationSessionException;
}
