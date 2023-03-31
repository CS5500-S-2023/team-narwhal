package edu.northeastern.cs5500.starterbot.model.authenticationChallenge;
import org.bson.types.ObjectId;

import lombok.Data;

// could be captcha, email or SMS
@Data
public class AuthenticationChallenge {    
    // The discord user attempting this challenge
    String discordMemberId;
    // The correct answer to the challenge
    String captchaAnswer;
    // The current state of the challenge
    AuthenticationState authstate;

    public AuthenticationChallenge(String memberID, String answer, AuthenticationState state){
        this.discordMemberId = memberID;
        this.captchaAnswer = answer;
        this.authstate = state;
    }
}
