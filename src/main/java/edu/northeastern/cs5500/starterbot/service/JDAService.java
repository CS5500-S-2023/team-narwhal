package edu.northeastern.cs5500.starterbot.service;

import edu.northeastern.cs5500.starterbot.exceptions.NoJDAInstanceException;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

@Singleton
@Slf4j
public class JDAService implements Service {
    @Inject
    JDAService() {
        register();
    }

    @Getter @Setter private JDA jda;

    @Override
    public void register() {
        log.info("JDA Service > register");
    }

    private void throwErrorIfNoJDAInstance() throws NoJDAInstanceException {
        if (jda == null) {
            throw new NoJDAInstanceException();
        }
    }

    public Guild getGuildById(@Nonnull String guildId) throws NoJDAInstanceException {
        throwErrorIfNoJDAInstance();

        return jda.getGuildById(guildId);
    }

    public User getUserById(@Nonnull String userId) throws NoJDAInstanceException {
        throwErrorIfNoJDAInstance();

        return jda.getUserById(userId);
    }

    public Role getRoleById(@Nonnull String roleId) throws NoJDAInstanceException {
        throwErrorIfNoJDAInstance();

        return jda.getRoleById(roleId);
    }
}
