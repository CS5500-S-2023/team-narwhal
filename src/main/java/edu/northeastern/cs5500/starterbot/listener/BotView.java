package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.controller.CaptchaAuthenticationController;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import nl.captcha.Captcha;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import edu.northeastern.cs5500.starterbot.authentication.AuthenticationHandler;
import net.dv8tion.jda.api.entities.User; 
import net.dv8tion.jda.api.entities.channel.ChannelType;

import javax.annotation.Nonnull;

public class BotView extends ListenerAdapter {
    private CaptchaAuthenticationController controller;

    @Inject Set<AuthenticationHandler> authenticationMethods;

    // potentially used for other types of controller
    public void setController(CaptchaAuthenticationController controller) {
        this.controller = controller;
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        //log.info("On guild member join");

        // Getting the user to open a private channel with
        User member = event.getMember().getUser();

        // Creating message string to send user in private channel
        String welcomeMessage =
                String.format(
                        "Welcome to %s! Before you can get access to the server, please verify using one of the following methods:",
                        event.getGuild());

        // Builds the message and adds buttons
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();

        Set<Button> buttons = new HashSet<>();
        for (AuthenticationHandler auth : authenticationMethods) {
            buttons.add(auth.createButton());
        }
        messageCreateBuilder.addActionRow(buttons).setContent(welcomeMessage);

        // Open private channel with user
        member.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(messageCreateBuilder.build()))
                .queue();
    }


    // view produces the buttons, user choose an option, then calls the controller to begin
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        switch (event.getButton().getId().split(":")[1]) {
            case "captcha":
                controller.begin(event);
                //onRequestedCaptcha(event);
                break;
            case "sms/text":
                //event.reply(onRequestedTwoFactor(event)).queue();
                break;
            case "email":
                //event.reply(onRequestedEmail(event)).queue();
                break;
            default:
                throw new IllegalStateException(event.getButton().getId());
        }
        event.getMessage().editMessageComponents(List.of()).queue();
    }

    public void renderCaptcha(@Nonnull ButtonInteractionEvent event, Captcha captcha){
        FileUpload file =
                FileUpload.fromData(
                        imageToByteArray(Objects.requireNonNull(captcha.getImage())), "image.png");
        event.replyFiles(file).queue();
    }

    @SneakyThrows({IOException.class})
    @Nonnull
    static byte[] imageToByteArray(@Nonnull BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        return Objects.requireNonNull(stream.toByteArray());
    }
    
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event)
    {
        if (event.getChannelType() != ChannelType.PRIVATE)
        {
            // We only listen to direct/private messages
            return;
        }
    
        for (AuthenticationHandler auth : authenticationMethods) {
            if(auth.messageReceived(event)) {
                return;
            }
        }

        event.getMessage().reply("No authentication session found!").queue();
    }
}