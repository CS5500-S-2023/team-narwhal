package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import edu.northeastern.cs5500.starterbot.handler.join.JoinHandler;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/** A generic GuildMemberJoinListener that detects when a user joins a guild. */
@IgnoreInGeneratedReport
@Singleton
public class GuildMemberJoinListener extends ListenerAdapter {
    @Inject Set<JoinHandler> guildMemberJoinHandlers;

    @Inject
    public GuildMemberJoinListener() {
        super();
    }

    /** When a new user joins a server */
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        for (JoinHandler guildMemberJoinHandler : guildMemberJoinHandlers) {
            guildMemberJoinHandler.handleGuildMemberJoinEvent(event);
        }
    }
}
