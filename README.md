# News Subscriber
News Subscriber is a totally free web application, which lets you follow worldwide news with e-mail notifications on a certain frequency. </br>
Initially built for bachelor's thesis, but it also serves for increasing my skills and trying new tech.

<!-- Live demo [_here_](https://www.example.com). <!-- If you have the project hosted somewhere, include the link here. -->

![News Subscriber - Animated gif demo](https://github.com/kacperkadziolka/kacperkadziolka/blob/main/Animation.gif)

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Contact](#contact)
<!-- * [License](#license) -->


## General Information
- Backend is built with Java + Spring Boot
- Frontend is handled by Thymeleaf
- CI / CD Pipeline is created via Jenkins hosted on AWS EC2, currently disabled due to the operational costs
- It's intend for free newsletter via email
- I created it for purpose of Bachelor's thesis
- It works like a place for enhancing my programming skills


## Technologies Used
- Java 17
- Spring Boot 2.7.5
- Docker
- Jenkins


## Features
Ready features:
- Fetch news from external API on given topic
- Send asynchronously newsletter emails with certain frequency
- Every subscription stored in DB, with option to unsubscribe
- Security login/registration form with single factor-authentication
- CI / CD Pipeline created with Docker, Jenkins, AWS ECR and ECS


## Setup
To run locally, create the application.properties file.

```
# NEWS API KEY
external.api.key=  <-- api key from News API

# GMAIL / SPRING MAIL CONFIGURATION
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=  <-- credentials for gmail mailbox
spring.mail.password=  <-- credentials for gmail mailbox
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# H2 Database
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

## Project Status
Project is: _in progress_


## Room for Improvement

TO DO:
- Exception handling
- Handle the duplicate emails in database
- Host on AWS
- Migrate to external database


## Contact
Created by [Kacper Kadziolka](https://github.com/kacperkadziolka) - feel free to contact me!


<!-- Optional -->
<!-- ## License -->
<!-- This project is open source and available under the [... License](). -->

<!-- You don't have to include all sections - just the one's relevant to your project -->
