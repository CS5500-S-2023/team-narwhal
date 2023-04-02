package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.view.UserEnterView;


public class UserEnterController {

    private UserEnterView userEnterView;

    @Inject
    UserEnterController(UserEnterView userEnterView){
        this.userEnterView = userEnterView;
    }

    public void handlerUserEvent(@Nonnull GuildMemberJoinEvent event){
        // Getting the user to open a private channel with
        User member = event.getMember().getUser();

        MessageCreateBuilder welcomeMsg = userEnterView.generateWelcomeMsg();
        // Open private channel with user
        member.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(welcomeMsg.build()))
                .queue();     
    }


}