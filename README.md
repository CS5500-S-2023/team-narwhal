# CS5500 Final Project: SpamFree Bot in Discord

## About
The SpamFree bot allows discord server owners to verify their members upon joining the server by solving challenges, such as CAPTCHA challenges. Adding the SpamFree bot will help prevent unauthroized users and spam bots from entering the server, providing members a more secure environment.

Our bot allows users to choose from three authentication methods:

![Screenshot 2023-04-27 at 4 52 10 PM](https://user-images.githubusercontent.com/90870823/235014260-5d2a9ae6-e807-4056-96e5-0c5c65fd67f1.png)

Our authentication process is easy to navigate:

![Screenshot 2023-04-27 at 4 52 27 PM](https://user-images.githubusercontent.com/90870823/235014314-74a7327f-27b7-47b1-b730-776efdf6e436.png)

## Getting Stated
You can join our [test server](https://discord.gg/vjguSzRv) where the bot is added to test the functionalities of the bot.

You can use [this link](https://discord.com/api/oauth2/authorize?client_id=1076634705862066187&permissions=8&scope=bot) to add the bot to your server.

You can follow the instructions below if you wish to customize the bot.

<details>
  <summary>Show more</summary>

### How to Install and Run the Bot Locally
1. Download the git repo or clone the repo to your local develop environment

![Screenshot 2023-04-27 at 3 18 53 PM](https://user-images.githubusercontent.com/90870823/235008051-c074bf1e-25d5-4dd0-9546-25dad29df5fd.png)

2. Make sure you sign up for a [Discord developer account](https://discord.com/developers/docs/intro) and are familiar with creating an application

3. Adding the bot to your server

After you've created an application, navigate to your application and click on the **OAuth2** tab
Click on the **URL Generator** and select **Bot**

![Screenshot 2023-04-27 at 3 20 37 PM](https://user-images.githubusercontent.com/90870823/235008401-d08f5ce5-b159-42da-b440-13fceeca818a.png)

After you've seleced "Bot" you should see an expanded "Bot permissions" panel, select "Administrator

![Screenshot 2023-04-27 at 4 01 36 PM](https://user-images.githubusercontent.com/90870823/235008802-9550e19b-82f0-4fa0-b510-458ad58619d5.png)

Copy and paste the Generated URL to your web browser, hit enter and select the server you want to add the bot to

![Screenshot 2023-04-27 at 3 23 38 PM](https://user-images.githubusercontent.com/90870823/235008931-d988de80-e5f3-486c-b664-cb7459ff312a.png)

Hit "Authorize" and your bot should be added to your chosen Discord server

![Screenshot 2023-04-27 at 3 23 48 PM](https://user-images.githubusercontent.com/90870823/235008995-63faea62-1f51-4572-b4e7-c09b6b7b5b1e.png)

![Screenshot 2023-04-27 at 3 24 28 PM](https://user-images.githubusercontent.com/90870823/235009001-d46e76fb-296f-4e97-8cc5-0ba29ca7c836.png)

You should see your bot status shown as a green circle, indicating it's online in Discord

![Screenshot 2023-04-27 at 3 25 48 PM](https://user-images.githubusercontent.com/90870823/235009005-691f5d2c-7996-4ae1-888c-8fab1c274f06.png)

4. Configurate bot token

Notice that the bot is currently offline, to run the bot, you need to configurate an environment variable "BOT_TOKEN"

First, you need to generate the bot token. Navigate to Discord developer portal and select the **Bot** tab

![Screenshot 2023-04-27 at 3 29 33 PM](https://user-images.githubusercontent.com/90870823/235009174-964ba35a-16b6-4ba5-82e3-89eccaf0e587.png)

Click on reset token and hit yes

![Screenshot 2023-04-27 at 3 29 40 PM](https://user-images.githubusercontent.com/90870823/235009254-8ae8b693-5dad-45d8-ae11-03eb6924fd54.png)

You should see a random string generated in the grey area, note that our bot is greyed out for security purpose

![Screenshot 2023-04-27 at 3 29 51 PM](https://user-images.githubusercontent.com/90870823/235009385-3997002a-9417-4343-a548-276b4f425c64.png)

Copy and paste this bot token to be set as an environment variable.

To set the bot token as an environment variable, you can either run the command shown below in terminal:
```ruby
export BOT_TOKEN="your_bot_token"
```
You can also add the environment variable in the run configuration, e.g. in IntelliJ:

![235009800-5072221e-18e2-49a1-9132-18d260d4b3db](https://user-images.githubusercontent.com/90870823/235015194-f89290db-7ba6-4a68-85aa-45ba50c22f4c.png)

5. Start you bot

After adding the bot to your Discord server and configuring the bot token, you are ready to run the bot.
You can run the bot by typing in the following command in terminal:

```ruby
./gradlew run
```
You can also run the bot by clicking on the run button in the App.java class. Note that this is IDE dependent.

You should see the following message if your bot is running.

![Screenshot 2023-04-27 at 5 13 04 PM](https://user-images.githubusercontent.com/90870823/235016309-0a4dafa0-fb32-4bb8-8ca0-1c099d981840.png)

6. Run /setup command

After adding the bot to your server and the bot is running, you MUST run the /setup command. This command change the default permission setting for anyone joining the server. This will ensure the security of your server even when the bot is down.

You can run the /setup command by typing "/setup" in the text channel.

![Screenshot 2023-04-27 at 4 37 14 PM](https://user-images.githubusercontent.com/90870823/235016220-89149b97-0811-4e22-b243-f5bde4e79a1d.png)

After running this command, you should see a success message

<img width="345" alt="Screenshot 2023-04-27 at 3 52 10 PM" src="https://user-images.githubusercontent.com/90870823/235012084-6868122e-e1f0-459d-874e-2386e084f506.png">

7. /verify-all command

You can also run /verify-all command to add a verified role to all the members before you added the bot to the server, so that those members don't have to verify themselevs again.

### Deploy app on remoter server
You can also launch the bot on a cloud hosting platform, we recommend [fly.io](https://fly.io/).

### Best Practices
When building and testing the bot, we recommend running the following commands:
Before you run the bot after each development session, run the command below in terminal to check styling issues:

```ruby
./gradlew build :spotlessApply
```
After adding tests, run the command below to view the Jacoco coverage reports:
```ruby
cd build/reports/jacoco/test/html
python3 -m http.server 8000
```
</details>

## Idea and Design Process
Please refer to our project [shared drive](https://drive.google.com/drive/folders/1e_y-lF_UVHFFMFOBv5O8v1xZfMVsw-mu?usp=share_link) for detailed bot design idea and our progress.

## Credits
This project was developed collaboratively by Matthew Fox (fox.mat@northeastern.edu) and Peiying Li (li.peiyi@northeastern.edu).

## Acknowledgement
This project was built on top of Professor Alexander Lash's starter bot provided in his CS5500 class at Northeastern University, Seattle.
