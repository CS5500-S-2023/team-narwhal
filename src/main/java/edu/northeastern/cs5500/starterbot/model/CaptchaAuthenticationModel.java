package edu.northeastern.cs5500.starterbot.model;
import edu.northeastern.cs5500.starterbot.model.authenticationChallenge.AuthenticationChallenge;
import edu.northeastern.cs5500.starterbot.model.authenticationChallenge.AuthenticationState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;
import java.util.HashMap;
import nl.captcha.Captcha;

@Data
@EqualsAndHashCode(callSuper=false)
// this class serves as the model captcha challenge
public class CaptchaAuthenticationModel {

    // memberID and the Authentication challenge they choose
    private Map<String, AuthenticationChallenge> captchaMap = new HashMap<>();

    public Captcha generateCaptcha(String memberId) {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        captchaMap.put(memberId, new AuthenticationChallenge(memberId, captcha.getAnswer(), AuthenticationState.WAITING_FOR_RESPONSE));
        return captcha;
    }

    public void verifyCaptcha(String userId, String solution) {
        // do something
        //return False;
    }    
}
