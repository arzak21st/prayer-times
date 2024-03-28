# Prayer Times ☕

A **Swing** desktop application that obtains 
[Islamic prayer times](https://en.wikipedia.org/wiki/Salah_times) 
of a specific month, and places them as Google Calendar events within a CSV file, 
which can then be imported into your Google Calendar. </br>

The prayer times are obtained from 
[timesprayer.com](https://timesprayer.com/en/). </br>

## Demo 💻

- ### Requirements

  - [Java 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) or higher.

- ### Steps

  - Download [this JAR file](https://github.com/arzak21st/prayer-times/releases/download/v1.0-SNAPSHOT/prayer-times-1.0-SNAPSHOT.jar).
  - Open your command-line and navigate to where your downloaded JAR file is located.
  - Run the JAR file with the following command:
      ```
      java -jar prayer-times-1.0-SNAPSHOT.jar
      ```
      > See [How It Works](#how-it-works-). </br>

## How It Works 📄

Here is how the application looks like: </br>

  ![View](https://github.com/arzak21st/prayer-times/assets/95937352/8e5490df-d9fe-4043-b7e5-30e2ed4aa2a6)

When you press the **Generate CSV File** button, the application starts the process of generating a CSV file based on the above inputs. </br>

- First, the application generates a URL that leads to the web page where the target prayer times exist. </br>
This URL is generated based on the values of the **City**, **Year**, and **Month** inputs. </br>
In my case for example, the application is going to generate the 
[URL](https://timesprayer.com/en/list-prayer-in-marrakech-2024-5.html) 
that leads to the web page where **Marrakech** prayer times in **May**-**2024** exist. </br>

- Next, the application tries to connect to the generated URL. </br>
On a successful connection, the application fetches the prayer times data found within the web page, and prepares it to be written as events within a CSV file. </br>
The value of the **Duration** input is used here to set the end time of each event. </br>
In my case for example, the end time of each event is going to be after **20** minute. </br>

- Last, the application creates a CSV file, and writes the prepared data as events within it, based on 
[this template](https://docs.google.com/spreadsheets/d/1k0eJMoytqFNn6G2QS3cEo2Of350lKF6x3gbEvbfMnkA/edit#gid=1). </br>
The **Directory** input is used here to specify the location where the CSV file is going to be created. </br>
In my case for example, the CSV file is going to be created within my **Desktop**. </br>

Once the CSV file is generated, you can press the **Import CSV File** button in order to import it to your Google Calendar. 
All you need to provide is the ID of the calendar that you want to import your CSV file to. </br>
The ID of a calendar can be found within its settings. </br>

> [!IMPORTANT]
> Hate to say it, but using the **Import CSV File** button requires some extra configuration to be done. </br>
> You can still import your CSV file(s) using the 
> [import feature](https://support.google.com/calendar/answer/37118?hl=en&co=GENIE.Platform%3DDesktop) 
> provided by Google Calendar, but in case you'd like to use the import feature provided by this application, follow the 
> [Installation](#installation-) 
> instructions. </br>

## Installation 🔌

- ### Requirements

  - [Java 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) or higher.
  - [Maven](https://maven.apache.org/download.cgi).
  - [Git](https://git-scm.com/downloads).
  - [NetBeans 19](https://netbeans.apache.org/front/main/download/nb19/).
    > You can use any other Java IDE, just make sure it uses the mentioned **Java 11**, and **Maven**. </br>

- ### Steps

  - Press the **Fork** button (top right the page) to make a copy of this project on your own GitHub account.
  - Open **Git Bash** and navigate to where you want your forked project to be cloned.
  - Clone the project with the following command:
      ```
      git clone https://github.com/your-username/your-forked-project-name.git
      ```
      > Replace `your-username` with the actual username of your GitHub account, and `your-forked-project-name` with the actual name of your forked project. </br>
  - Open **NetBeans** (or any other Java IDE) and try to import your cloned project.
  - [Create an OAuth Client ID for the project](https://developers.google.com/workspace/guides/get-started): (Optional)
      - [Create a Google Cloud project](https://console.cloud.google.com/projectcreate).
        > **Project name**: google-calendar-api </br>
      - [Enable the Calendar API in your Google Cloud project](https://console.cloud.google.com/apis/library/calendar-json.googleapis.com).
      - [Configure your OAuth consent](https://console.cloud.google.com/apis/credentials/consent).
        > **User Type**: External </br>
        > **App name**: prayer-times </br>
        > **User support email**: your-email </br>
        > **Email addresses**: `your-email` </br>
        > **Scopes**: `https://www.googleapis.com/auth/calendar.events` (View and edit events on all of your calendars) </br>
        > **Test users**: `your-email` </br>
      - [Create your OAuth Client ID](https://console.cloud.google.com/apis/credentials/oauthclient).
        > **Application type**: Desktop app </br>
        > **Name**: prayer-times-client-id </br>
  - Download your created OAuth Client ID as a JSON file.
  - Place your downloaded JSON file within the
  [src/main/resources/others](https://github.com/arzak21st/prayer-times/tree/main/src/main/resources/others) 
  directory, and rename it to `credentials.json`.

> [!CAUTION]
> DO NOT include the JSON file when pushing your changes. </br>
> DO NOT make it publicly available in any way, NEVER. </br>
> See
> [why](https://developers.google.com/terms#b_confidential_matters). </br>

## Additional Information 🔥

This application creates a Properties file named `prayer-times-preferences.properties` within your working directory, 
to remember your last selected options. </br>

Made with Love ❤️ and Taqwa 📿. </br>
