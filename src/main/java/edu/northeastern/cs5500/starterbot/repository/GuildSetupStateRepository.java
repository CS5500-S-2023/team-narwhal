package edu.northeastern.cs5500.starterbot.repository;

import edu.northeastern.cs5500.starterbot.model.GuildState;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Repository of guild setup states. */
@Singleton
public class GuildSetupStateRepository extends InMemoryRepository<GuildState> {
    @Inject
    public GuildSetupStateRepository() {
        super();
    }

    /**
     * Retrieves guild's setup state
     *
     * @return the GuildState that was requested
     */
    @Nullable
    public GuildState get(@Nonnull String guildId) {
        return collection.values().stream()
                .filter(guildState -> guildState.getGuildId().equals(guildId))
                .toArray(GuildState[]::new)[0];
    }

    /**
     * Removes a GuildState from the repository.
     *
     * @param guildId - The guild we are removing from the repoistory.
     */
    public void delete(@Nonnull String guildId) {
        GuildState[] guildStates =
                collection.values().stream()
                        .filter(guildState -> guildState.getGuildId().equals(guildId))
                        .toArray(GuildState[]::new);
        for (GuildState guildState : guildStates) {
            collection.remove(guildState.getId());
        }
    }
}
