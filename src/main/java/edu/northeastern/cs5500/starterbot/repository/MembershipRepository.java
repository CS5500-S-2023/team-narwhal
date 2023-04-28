package edu.northeastern.cs5500.starterbot.repository;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.model.Membership;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Repository of user server memberships */
@Singleton
@IgnoreInGeneratedReport
public class MembershipRepository extends InMemoryRepository<Membership> {
    @Inject
    public MembershipRepository() {
        super();
    }

    /**
     * Retrieves user's membership.
     *
     * @param userId - User's ID we are retrieving the membership for.
     * @return user's membership.
     */
    @Nullable
    public Membership get(@Nonnull String userId) {
        List<Membership> memberships =
                collection.values().stream()
                        .filter(membership -> membership.getUser().getId().equals(userId))
                        .collect(Collectors.toList());

        if (memberships.isEmpty()) {
            return null;
        } else {
            return memberships.get(0);
        }
    }

    /**
     * Removes the user's membership from the MembershipRepository.
     *
     * @param userId - User's ID we are removing from the MembershipRepository.
     */
    public void delete(@Nonnull String userId) {
        List<Membership> memberships =
                collection.values().stream()
                        .filter(membership -> membership.getUser().getId().equals(userId))
                        .collect(Collectors.toList());

        for (Membership membership : memberships) {
            collection.remove(membership.getId());
        }
    }
}
