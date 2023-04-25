package edu.northeastern.cs5500.starterbot.model;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;
import lombok.Builder;
import lombok.Data;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.bson.types.ObjectId;

@Data
@Builder
@IgnoreInGeneratedReport
public class Membership implements Model {
    ObjectId id;

    // The discord user attempting this challenge, currently using event userId not memberId
    User user;
    // The server the user is authenticating for
    Guild guild;
}
