import edu.northeastern.cs5500.starterbot.view.UserEnterView;

public class ButtonClickController {

    private Set<ButtonHandler> buttons;
    private Set<AuthenticationHandler> authenticationMethods;

    // TODO: replace with a set of views
    private UserEnterView userEnterView;




    public ButtonClickController(Set<ButtonHandler> buttons, Set<AuthenticationHandler> authenticationMethods){

    }

    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
   

        log.info("onButtonInteraction: {}", event.getButton().getId());

        String id = event.getButton().getId();
        Objects.requireNonNull(id);
        String handlerName = id.split(":", 2)[0];

        for (ButtonHandler buttonHandler : buttons) {
            if (buttonHandler.getName().equals(handlerName)) {
                buttonHandler.onButtonInteraction(event);
                return;
            }
        }

        for (AuthenticationHandler auth : authenticationMethods) {
            if (auth.getName().equals(handlerName)) {
                auth.onButtonInteraction(event);
                return;
            }
        }

        log.error("Unknown button handler: {}", handlerName);
    }

}