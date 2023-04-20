package edu.northeastern.cs5500.starterbot.repository;

import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;
import java.util.Collection;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class ChallengeRepository implements GenericRepository<AuthenticationChallenge> {

    HashMap<ObjectId, AuthenticationChallenge> collection;

    @Inject
    public ChallengeRepository() {
        collection = new HashMap<>();
    }

    public AuthenticationChallenge get(@Nonnull ObjectId id) {
        return collection.get(id);
    }

    public AuthenticationChallenge get(@Nonnull String userId) {
        for (AuthenticationChallenge challenge : getAll()) {
            if (challenge.getEventUserId().equals(userId)) {
                return challenge;
            }
        }
        return null;
    }

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

    @Override
    public AuthenticationChallenge update(@Nonnull AuthenticationChallenge challenge) {
        collection.put(challenge.getId(), challenge);
        return challenge;
    }

    @Override
    public void delete(@Nonnull ObjectId id) {
        collection.remove(id);
    }

    @Override
    public Collection<AuthenticationChallenge> getAll() {
        return collection.values();
    }

    @Override
    public long count() {
        return collection.size();
    }
}
