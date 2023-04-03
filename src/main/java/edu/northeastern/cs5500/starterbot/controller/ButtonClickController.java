import edu.northeastern.cs5500.starterbot.authentication.AuthenticationType;
import edu.northeastern.cs5500.starterbot.view.UserEnterView;

public class ButtonClickController {

    private Map<AuthenticationType, AuthenticationHandler> authenticationMethods;

    @Inject
    public ButtonClickController(Map<AuthenticationType, AuthenticationHandler> authenticationMethods){
        this.authenticationMethods = authenticationMethods;
    }

    // entry point, route to different authentication methods and other user actions
    // all button clicks
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        log.info("onButtonInteraction: {}", event.getButton().getId());

        String id = event.getButton().getId();
        Objects.requireNonNull(id);
        String handlerCategory = id.split(":", 2)[0]; // e.g. authenticate, use if we add other buttons
        String handlerType = id.split(":", 2)[1];

        // could have another switch statement to handle other handler types

        switch (handlerType) {
            case "captcha":
                
                authenticationMethods.get(AuthenticationType.CAPTCHA).onButtonInteraction(event);
                break;
            case "text":
                authenticationMethods.get(AuthenticationType.TEXT).onButtonInteraction(event);
                break;
            case "email":
                authenticationMethods.get(AuthenticationType.EMAIL).onButtonInteraction(event);
                break;
            default:
                throw new IllegalStateException(event.getButton().getId());
        }
        log.error("Unknown button handler: {}", handlerName);
    }

}