
/**
 * This interface will receive notification from the model that a user has joined a server, then 
 * call methods to authenticate the user, after the authentication is complete (in the model), 
 * the controller will get the status of verification and tell the model 
 * that the user can have access to the entire server.
 * 
 * Q: think about the data that's being transfered (using JDA) and 
 * how is this working with the eventlistener and handler?
 */
public interface UserAuthenticationController {
    // 


}