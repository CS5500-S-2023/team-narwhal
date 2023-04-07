package edu.northeastern.cs5500.starterbot.model;

/**
 * The states of a {@link AuthenticationChallenge}.
 */
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

    /**
     * If the program terminates based on an AuthenticationState.
     * @return true if it terminates the program
     */
    public boolean isTerminal() {
        return switch (this) {
            case VERIFIED, TOO_MANY_ATTEMPTS, FAILED_TO_SEND -> true;
            default -> false;
        };
    }
}
