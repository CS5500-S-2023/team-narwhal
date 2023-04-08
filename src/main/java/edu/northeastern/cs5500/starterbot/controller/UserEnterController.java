package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.service.UserEnterService;
import edu.northeastern.cs5500.starterbot.view.BotView;


import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

/**
 * Delegate the {@link BotView} and use the {@link UserEnterService} to render a welcome message
 * when the user first joins the server.
 */
@Slf4j
public class UserEnterController {

    private BotView botView;

    private UserEnterService userEnterService;

    @Inject
    UserEnterController(BotView botView, UserEnterService userEnterService) {
        this.botView = botView;
        this.userEnterService = userEnterService;
    }

    /**
     * Handle user joining the server event.
     * @param event user joining the server event as a GuildMemberJoinEvent
     */
    public void handleUserJoinEvent(@Nonnull GuildMemberJoinEvent event) {
        // log.info("On guild member join");
        // log.info("The member ID in the guild is : " + event.getJDA().getGuilds().get(0).getMemberByTag(event.getUser().getAsTag()).getId());
        // String memberTag = event.getMember().getUser().getAsTag();
        // for (Member m : event.getJDA().getGuilds().get(0).getMembers()){
        //     log.info("The member from the guild is " + m.getId());
        //     if (m.getUser().getAsTag().equals(memberTag)){
        //         log.info("The current member from the guild is " + m.getId());
        //     }
        // }
        // log.info("The user ID from the event user is : " + event.getUser().getId());
        // Getting the user to open a private channel with
        User user = event.getUser();

        // add the event user id and the guild id to the repo for later use
        String userId = user.getId();
        String guildId = event.getGuild().getId();
        userEnterService.mapEventUserGuildId(userId, guildId);

        // use the view to  build the welcome msg
        String guildName = event.getGuild().getName();
        MessageCreateBuilder welcomeMsg = botView.generateWelcomeMsg(guildName);

        // Open a private channel with the user and send the welcome msg
        // TODO: add try catch
        PrivateChannel channel = user.openPrivateChannel().complete();
        channel.sendMessage(welcomeMsg.build()).queue();
    }
}
