package edu.northeastern.cs5500.starterbot.handler.join;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public interface JoinHandler {
    void handleGuildMemberJoinEvent(GuildMemberJoinEvent event);
}
