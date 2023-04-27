package edu.northeastern.cs5500.starterbot;

import static spark.Spark.get;
import static spark.Spark.port;

import edu.northeastern.cs5500.starterbot.annotation.IgnoreInGeneratedReport;

/** Starting point of the program - run the bot by running the main method. */
@IgnoreInGeneratedReport
public class App {

    public static void main(String[] arg) throws InterruptedException {

        DaggerBotComponent.create().bot().start();

        port(8080);

        get("/", (request, response) -> "{\"status\": \"OK\"}");
    }
}
