# CS5500 Final Project: SpamFree Bot in Discord

## About the Project
The SpamFree bot allows discord server owners to verify their members upon joining the server by solving challenges, such as CAPTCHA challenges. Adding the SpamFree bot will help prevent unauthorized users and spam bots from entering the server, providing members a more secure environment.

Our bot allows users to choose from three authentication methods:

![Screenshot 2023-04-27 at 4 52 10 PM](https://user-images.githubusercontent.com/90870823/235014260-5d2a9ae6-e807-4056-96e5-0c5c65fd67f1.png)

Our authentication process is easy to navigate:

![Screenshot 2023-04-27 at 4 52 27 PM](https://user-images.githubusercontent.com/90870823/235014314-74a7327f-27b7-47b1-b730-776efdf6e436.png)

To test this bot join our [test server](https://discord.gg/naFqaaJK). You can also refer to the project [shared drive](https://drive.google.com/drive/folders/1e_y-lF_UVHFFMFOBv5O8v1xZfMVsw-mu?usp=share_link) for our detailed bot design ideas and our progress.

### Built With

 - APIs
    - [JDA](https://jda.wiki/)
    - [SimpleCaptcha](https://simplecaptcha.sourceforge.net/)
 - Deployment
    - [Fly.io](https://fly.io/)
 - Frameworks & Libraries
    - [Dagger](https://dagger.dev/)
    - [Gradle](https://gradle.org/)
    - [Guava](https://github.com/google/guava)
    - [JUnit](https://junit.org/junit5/)
    - [Lombok](https://projectlombok.org/)
    - [Maven](https://maven.apache.org/)
    - [MongoDB](https://www.mongodb.com/)
    - [Truth](https://truth.dev/)

## Installation

### Set up Discord Developer Portal Account
1. Sign up for a [Discord developer account](https://discord.com/developers/applications)
2. Familiarize yourself with creating a Discord application using [Discord documentation](https://discord.com/developers/docs/intro).
2. Create a new application

### Add Bot to Server
1. Navigate to your [application](https://discord.com/developers/applications)
2. Click on the **OAuth2** tab
3. Click on the **URL Generator**
4. Select **Bot**
  ![OAuth2 URL Generator](https://user-images.githubusercontent.com/90870823/235008401-d08f5ce5-b159-42da-b440-13fceeca818a.png)
5. After you've seleced "Bot" you should see an expanded "Bot permissions" panel, select "Administrator"
  ![Bot permissions](https://user-images.githubusercontent.com/90870823/235008802-9550e19b-82f0-4fa0-b510-458ad58619d5.png)
6. Copy the generated URL at the bottom
7. Paste the generated URL to your web browser. An invitation prompt should show up.

    ![Bot invite to server](https://user-images.githubusercontent.com/90870823/235008931-d988de80-e5f3-486c-b664-cb7459ff312a.png)
8. Select the server you want to add the bot to
    - If you do not have a
    server or don't want to create one, you can join our [test server](https://discord.gg/naFqaaJK) where the bot is added to test bot functionalities.
9. Hit "Authorize" and your bot should be added to your chosen Discord server
    ![Authorize prompt](https://user-images.githubusercontent.com/90870823/235008995-63faea62-1f51-4572-b4e7-c09b6b7b5b1e.png)

    ![Authorized](https://user-images.githubusercontent.com/90870823/235009001-d46e76fb-296f-4e97-8cc5-0ba29ca7c836.png)

You should see your bot in the chosen server.

### Generate & Configure Bot Token

Notice that the bot is currently offline. To run the bot, you need to configure a `BOT_TOKEN` environment variable.

![Offline bot](https://user-images.githubusercontent.com/90870823/235009005-691f5d2c-7996-4ae1-888c-8fab1c274f06.png)

1. Navigate to [Discord Developer Portal](https://discord.com/developers/applications)
2. Select the **Bot** tab
  ![Bot tab](https://user-images.githubusercontent.com/90870823/235009174-964ba35a-16b6-4ba5-82e3-89eccaf0e587.png)

3. Click on reset token
4. Hit "Yes, do it!"

  ![Reset bot token prompt](https://user-images.githubusercontent.com/90870823/235009254-8ae8b693-5dad-45d8-ae11-03eb6924fd54.png)

5. Under `TOKEN`, you should see a random string generated.
![Token](https://user-images.githubusercontent.com/90870823/235009385-3997002a-9417-4343-a548-276b4f425c64.png)
*NOTE: Our bot token is obfuscated for security reasons.*

6. Copy the token
7. Set the bot token as an environment variable
    - **Option 1**: Run the command shown below in terminal.
      - Replace `your_bot_token` with the token we just copied.
      ```shell session
      export BOT_TOKEN="your_bot_token"
      ```

    - **Option 2** Add the environment variable in your IDE configuration
      - For example: in IntelliJ, you can add environment variables in the run configuration
      ![IDE configuration](https://user-images.githubusercontent.com/90870823/235015194-f89290db-7ba6-4a68-85aa-45ba50c22f4c.png)

## Usage

### Adding Bot to Server
You can either follow the installation steps above or click [this link](https://discord.com/api/oauth2/authorize?client_id=1076634705862066187&permissions=8&scope=bot) to invite the bot into your server.

### Setting Up Your Server
After adding the bot to your server, run the `/setup` command. This command will remove *most* permissions for `@everyone`. This prevents unverified and new server members from interacting with the server. By setting the default permissions to be mostly disabled, it will ensure the security of your server even when the bot is down.

You can run the command by typing `/setup` in any text channel in the server.

![Setup command](https://user-images.githubusercontent.com/90870823/235016220-89149b97-0811-4e22-b243-f5bde4e79a1d.png)

`/setup` command has an optional `verify-all` parameter. If set to true, the bot will add the `Verified` role to all existing members of the server. If set to false or no option is given, the bot will only update server permissions and all server members would need to authenticate themselves before they can regain access to the server.

**NOTE**: If your server already has a `Verified` role, we suggest either duplicating it with a new name or deleting it and editting permissions to the bot-generated `Verified` role after setup.

After running this command, you should see a success message.

<img width="345" alt="Success message" src="https://user-images.githubusercontent.com/90870823/235012084-6868122e-e1f0-459d-874e-2386e084f506.png">

We also have the `/verify-all` command that also adds the `Verified` role to every *current* member of the server. You can use `/verify-all` anytime you need to automatically assign `Verified` roles.

`/setup` and `/verify-all` commands can only be called by users and roles that have "Administrator" permissions. We recommend creating a `Moderator` role to let users other than the server owner use these commands.

### Authenticating

After completing setup, any new user that joins the server will not have permissions to interact with the server. They may see something like this:

<img width="345" alt="Blocked server" src="https://user-images.githubusercontent.com/88064362/235094216-06b7eb4b-369c-45fd-b09c-8f5ed24f1efd.png">

As soon as a user joins a server, the bot will send them a private message to authenticate themselves.

<img src="https://user-images.githubusercontent.com/88064362/235096251-eaeb5855-a72c-4205-96e2-ef6e0f68a6d4.png" height="200px">

The bot presents the user with three options to authenticate themselves: `CAPTCHA`, `Email`, and `SMS/Text`.

<img width="500" alt="Prompt" src="https://user-images.githubusercontent.com/88064362/235096249-a944382f-de94-433a-b30b-8a7eebb8ffc8.png">

**NOTE**: Currently, users can only authenticate using `CAPTCHA`. `Email` and `SMS/Text` will be available in future implementations.

The user clicks on `CAPTCHA` and the bot replies with a CAPTCHA challenge.

<img width="345" alt="CAPTCHA Challenge" src="https://user-images.githubusercontent.com/88064362/235096252-6993e831-9540-476b-9aea-098367ef6893.png">

If the user replies with a wrong answer, the bot will prompt the user again.

<img width="345" alt="Wrong answer" src="https://user-images.githubusercontent.com/88064362/235096250-a5c6349d-bde9-4fef-b9b9-f1168e9a5824.png">

If the user replies with the right answer, the bot will give them the `Verified` role, which allows the user to interact with the server.

<img width="345" alr="Success" src="https://user-images.githubusercontent.com/88064362/235096253-3ccb4861-0787-44da-91ff-09c7729c578d.png">

## Development

### Prerequisites

SimpleCaptcha requires `fontconfig` to be able to generate the CAPTCHA images. To install, run this command in the terminal:

```shell session
sudo apt install fontconfig
```
If you get an error informing you that `fontconfig` could not be found. You might need to update first:
```shell session
sudo apt update
```

### How to Build

Run the following command:
```shell session
./gradlew build :spotlessApply
```

### How to Run Locally

Run the following command:
```shell session
./gradlew run
```

### How to Test

Run the following command:
```shell session
./gradlew test
```

To view code coverage report, run the following:
```shell session
cd build/reports/jacoco/test/html
python3 -m http.server 8000
```

### How to Deploy App
You can launch the bot on a cloud hosting platform, we recommend [fly.io](https://fly.io/).

## Idea and Design Process
Please refer to our project [shared drive](https://drive.google.com/drive/folders/1e_y-lF_UVHFFMFOBv5O8v1xZfMVsw-mu?usp=share_link) for our detailed bot design ideas and our progress.

## Credits
This project was developed collaboratively by Matthew Fox (fox.mat@northeastern.edu) and Peiying Li (li.peiyi@northeastern.edu).

## Acknowledgement
This project was built on top of Professor Alexander Lash's [starter bot](https://github.com/abl/bot) provided in his CS5500 class at Northeastern University, Seattle.
