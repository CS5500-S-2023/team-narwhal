package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.GuildStore;
import edu.northeastern.cs5500.starterbot.model.UserGuild;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.service.UserEnterService;
import edu.northeastern.cs5500.starterbot.view.BotView;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class UserEnterController {

    private BotView botView;

    private UserEnterService userEnterService;
    @Inject
    GuildStore guildStore;

    @Inject
    UserEnterController(BotView botView, UserEnterService userEnterService) {
        this.botView = botView;
        this.userEnterService = userEnterService;
    }

    public void handleUserEvent(@Nonnull GuildMemberJoinEvent event) {
        // Getting the user to open a private channel with
        User user = event.getUser();
        String userId = user.getId();
        Member member = event.getGuild().getMemberById(userId);
        //event.getGuild().addMember(new ProcessBuilder().environment().get("BOT_TOKEN"), event.getUser());
//        System.out.println(event.getUser().getAsTag());
//        for(Member m: event.getGuild().getMembers()){
//            System.out.println(m.getUser().getAsTag());
//        }
//        String userTag = event.getUser().getAsTag();

        guildStore.setGuild(event.getJDA().getGuilds().get(0));

        // add the user event id with the guild id - not necessary for now
        // TODO: store the Guild ID too? when the guild is added to multiple servers?
        //userEnterService.mapUserGuild(event.getUser().getId(), member.getId());

        MessageCreateBuilder welcomeMsg = botView.generateWelcomeMsg(event);
        // Open private channel with user
        // TODO: add try catch
        PrivateChannel channel = event.getUser().openPrivateChannel().complete();
        userEnterService.mapUserGuild(channel.getUser().getId(), member.getId());
        channel.sendMessage(welcomeMsg.build()).queue();
    }
}
