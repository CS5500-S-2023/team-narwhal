package edu.northeastern.cs5500.starterbot.model;

public enum AuthenticationState {
    // UNKNOWN in case we get a state from the DB that doesn't make sense
    UNKNOWN,
    // A challenge has been sent, but no replies yet
    WAITING_FOR_RESPONSE,
    // The user has tried and failed to answer the challenge at least once
    INCORRECT_RESPONSE,
    // [TERMINAL] The user successfully answered the challenge
    VERIFIED,
    // [TERMINAL] The user failed too many times
    TOO_MANY_ATTEMPTS,
    // [TERMINAL] The challenge could not be sent
    FAILED_TO_SEND;

    public boolean isTerminal() {
        switch (this) {
            case VERIFIED:
            case TOO_MANY_ATTEMPTS:
            case FAILED_TO_SEND:
                return true;
            default:
                return false;
        }
    }
}
