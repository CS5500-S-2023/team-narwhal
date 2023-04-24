package edu.northeastern.cs5500.starterbot.repository;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import java.util.Collection;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/** A class to store AuthenticationChallenges. */
@Singleton
@Slf4j
public class ChallengeRepository implements GenericRepository<AuthenticationChallenge> {

    HashMap<ObjectId, AuthenticationChallenge> collection;

    @Inject
    public ChallengeRepository() {
        collection = new HashMap<>();
    }

    /**
     * Gets the AuthenticationChallenge.
     *
     * @return an AuthenticationChallenge.
     */
    public AuthenticationChallenge get(@Nonnull ObjectId id) {
        return collection.get(id);
    }

    /**
     * Gets the AuthenticationChallenge using userId.
     *
     * @param userId - The user we want to get the AuthenticationChallenge for.
     * @return an AuthenticationChallenge.
     */
    public AuthenticationChallenge get(@Nonnull String userId) {
        for (AuthenticationChallenge challenge : getAll()) {
            if (challenge.getEventUserId().equals(userId)) {
                return challenge;
            }
        }
        return null;
    }

    /**
     * Adds an AuthenticationChallenge to the ChallengeRepository.
     *
     * @param challenge - The AuthenticationChallenge we are adding to the ChallengeRepository.
     * @return an AuthenticationChallenge.
     */
    @Override
    public AuthenticationChallenge add(@Nonnull AuthenticationChallenge challenge) {
        log.info(String.format("Adding challenge: %s", challenge.toString()));
        ObjectId id = challenge.getId();
        if (id == null) {
            id = new ObjectId();
            challenge.setId(id);
        }
        collection.put(id, challenge);

        log.info(collection.toString());
        return challenge;
    }

    /**
     * Replaces an AuthenticationChallenge in ChallengeRepository with an updated
     * AuthenticationChallenge.
     *
     * @param challenge - The AuthenticationChallenge we are updating.
     * @return an updated AuthenticationChallenge.
     */
    @Override
    public AuthenticationChallenge update(@Nonnull AuthenticationChallenge challenge) {
        collection.put(challenge.getId(), challenge);
        return challenge;
    }

    /** Deletes an AuthenticationChallenge from the ChallengeRepository. */
    @Override
    public void delete(@Nonnull ObjectId id) {
        collection.remove(id);
    }

    /**
     * Gets all the AuthenticationChallenges from the ChallengeRepository.
     *
     * @return a collection of AuthenticationChallenges.
     */
    @Override
    public Collection<AuthenticationChallenge> getAll() {
        return collection.values();
    }

    /**
     * Gets the size of the ChallengeRepository.
     *
     * @return the number of AuthenticationChallenges in the ChallengeRepository.
     */
    @Override
    public long count() {
        return collection.size();
    }
}
