package edu.northeastern.cs5500.starterbot.service;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

@Module
@Slf4j
public abstract class ServiceModule { // NOSONAR
    static String getBotToken() {
        return new ProcessBuilder().environment().get("BOT_TOKEN");
    }

    @Provides
    @Singleton
    static JDA provideJDA() {
        try {
            String token = getBotToken();
            if (token == null) {
                throw new IllegalArgumentException(
                        "The BOT_TOKEN environment variable is not defined.");
            }

            return JDABuilder.createDefault(
                            token,
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .setDisabledIntents(
                            GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.GUILD_EMOJIS_AND_STICKERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();

        } catch (Exception e) {
            log.error("Unable to start the bot", e);
        }
        return null;
    }
}
