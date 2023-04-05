package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.view.BotView;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class UserEnterController {

    private BotView botView;

    @Inject
    UserEnterController(BotView botView) {
        this.botView = botView;
    }

    public void handleUserEvent(@Nonnull GuildMemberJoinEvent event) {
        // Getting the user to open a private channel with
        User member = event.getMember().getUser();

        MessageCreateBuilder welcomeMsg = botView.generateWelcomeMsg(event);
        // Open private channel with user
        member.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(welcomeMsg.build()))
                .queue();
    }
}
