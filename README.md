# PriceReminder [![Java CI with Maven](https://github.com/arguvos/PriceReminder/actions/workflows/build.yml/badge.svg)](https://github.com/arguvos/PriceReminder/actions/workflows/build.yml)
PriceReminder is a telegram bot. It is designed to track changes in information on sites. For example, on the website of the market, the thing you are interested in is not currently on sale. And when it becomes available again, you would like to be notified. Using this bot, you can subscribe to any information on the site and receive a notification when it changes.

The bot is available in telegram at the following link: https://t.me/alert_change_bot

The bot supports the following commands:
- info - get info about bot
- add - create new task
- show - show tasks
- delete - delete task

The application is ready to work in the Google Cloud Platform. It runs on App Engine.

How the service works:
1. Using the "add" command, we create a new task in the telegram. When creating a task, the URL of the site and the Xpath of the element are specified to track changes.
2. You can view information about existing tasks and delete them using the "show" and "delete" commands.
3. Once an hour, the service checks tasks and if changes have occurred on the site, it sends a notification to the user. This functionality works by calling the "/execution/execute" endpoint. This endpoint is called once per hour using the job scheduler configured in [cron.yaml](https://cloud.google.com/appengine/docs/flexible/scheduling-jobs-with-cron-yaml).

## Dependencies
PriceReminder service uses:
- java 17
- lombok
- [micronaut](https://docs.micronaut.io/latest/guide/)
- hibernate
- junit
- maven
- telegrambots
- google cloud tools

PriceReminder also uses the following services which can be started with docker-compose:
- [postgres](https://hub.docker.com/_/postgres)

## Compile and run
For compile project and run test:
```
mvn clean package
```

For deploy service on GCP in AppEngine:
```
mvn clean package appengine:deploy
```

Dependent services, can be run by docker-compose:
- postgres

## Configuration
Basic configuration properties are passed through environment variables:
- BOT_TOKEN
- BOT_USERNAME
- DATASOURCES_JDBC_PASSWORD
- DATASOURCES_JDBC_URL 
- DATASOURCES_JDBC_USERNAME
- GOOGLE_APPLICATION_CREDENTIALS
AppEngine get this environment variables from [app.yaml](https://cloud.google.com/appengine/docs/flexible/reference/app-yaml?tab=java) file.