package edu.northeastern.cs5500.starterbot.repository;

import dagger.Module;
import dagger.Provides;
import edu.northeastern.cs5500.starterbot.model.AuthenticationChallenge;

@Module
public class RepositoryModule {
    // NOTE: You can use the following lines if you'd like to store objects in memory.
    // NOTE: The presence of commented-out code in your project *will* result in a lowered grade.
    @Provides
    public GenericRepository<AuthenticationChallenge> provideUserPreferencesRepository(
            InMemoryRepository<AuthenticationChallenge> repository) {
        return repository;
    }

    // @Provides
    // public GenericRepository<UserPreference> provideUserPreferencesRepository(
    //         MongoDBRepository<UserPreference> repository) {
    //     return repository;
    // }

    // @Provides
    // public Class<UserPreference> provideUserPreference() {
    //     return UserPreference.class;
    // }
}
