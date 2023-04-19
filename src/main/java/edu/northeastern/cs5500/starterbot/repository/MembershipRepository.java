package edu.northeastern.cs5500.starterbot.repository;

import edu.northeastern.cs5500.starterbot.model.Membership;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Repository of user server memberships */
@Singleton
public class MembershipRepository extends InMemoryRepository<Membership> {
    @Inject
    public MembershipRepository() {
        super();
    }

    /** Retrieves user's membership */
    @Nullable
    public Membership get(@Nonnull String userId) {
        return collection.values().stream()
                .filter(membership -> membership.getUser().getId().equals(userId))
                .toArray(Membership[]::new)[0];
    }

    public void delete(@Nonnull String userId) {
        Membership[] memberships =
                collection.values().stream()
                        .filter(membership -> membership.getUser().getId().equals(userId))
                        .toArray(Membership[]::new);
        for (Membership membership : memberships) {
            collection.remove(membership.getId());
        }
    }
}