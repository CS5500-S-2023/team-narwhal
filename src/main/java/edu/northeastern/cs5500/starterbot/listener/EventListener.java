package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.authentication.AuthenticationHandler;
import java.util.*;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.Guild;

@Slf4j
public class EventListener extends ListenerAdapter {
    private boolean messageSent = false;
    private boolean isVerified = false; // keep track of the user authentication status 
    private String answer = "";
    private String userId;
    private Map<String,  List<AuthenticationHandler>> captchaAnswers = new HashMap<>();

    @Inject Set<AuthenticationHandler> authenticationMethods;

    @Inject
    public EventListener() {
        super();
    }

    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        log.info("On guild member join");

        // Getting the user to open a private channel with
        User member = event.getMember().getUser();
        String memberId = event.getMember().getId();
        this.userId = memberId;
       
        // Creating message string to send user in private channel
        String welcomeMessage =
                String.format(
                        "Welcome to %s! Before you can get access to the server, please verify using one of the following methods:",
                        event.getGuild()) + ", this memberID is" + memberId;

        // Builds the message and adds buttons
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        Set<Button> buttons = new HashSet<>();
        List<AuthenticationHandler> auths = new ArrayList<AuthenticationHandler>();
        for (AuthenticationHandler auth : authenticationMethods) {
            buttons.add(auth.createButton());
            auths.add(auth);
        }
        captchaAnswers.put(memberId, auths);

        // after adding these buttons, the buttons handles user chosing an authentication type
        // and sending user an captcha
        messageCreateBuilder.addActionRow(buttons).setContent(welcomeMessage);

        // Open private channel with user
        member.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(messageCreateBuilder.build()))
                .queue();
    }

    // wait for user input, once user input something, verify
    // once user hit enter, verify
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getChannelType() != ChannelType.PRIVATE)
        {
            // We only listen to direct/private messages
            return;
        }

        if (!event.getAuthor().isBot() && !messageSent) {
            String userInput = event.getMessage().getContentRaw();
            String memberID = event.getAuthor().getId();
            this.answer = captchaAnswers.get(memberID).get(0).getAnswer();
            // is it always 0 for captcha?

            //event.getChannel().sendMessage("This answer id is" + memberID).queue();
            //event.getChannel().sendMessage("This user id is" + this.userId).queue();
            //event.getChannel().sendMessage("This answer is " + this.answer).queue();

            event.getChannel().sendMessage(verify(userInput, this.answer)).queue();
            messageSent = true;
        }
    }
        // TODO: keep track of how many times user has tried
        // keep track the user verified status

    public String verify(@Nonnull String userInput, @Nonnull String answer){
        if(userInput.equals(answer)){
            return "You solved the CAPTCHA correctly.\n";
        } else{
            return "Incorrect answer. Try again.\n";
        }  
    }
}
