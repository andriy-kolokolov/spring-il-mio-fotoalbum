## My Photoalbum
### Spring rest API + Vuejs simple photoalbum manager 

## Features
- Server - Authentication system using JWT token with expiration
- Client - Components render like navigation, buttons ecc.. based on auth status
- authenticated users can:
  - create, read, update, delete their own photos.
  - send messages to other users using photo card action button 'message'
  - view received and sent messages on related page 'Messages'
- unauthenticated users can:
  - view list of all photos
  - register as new user
  - login

## How to run application ?

Clone repository using:
```shell
git clone https://github.com/andriy-kolokolov/spring-il-mio-fotoalbum.git
```

### Run client:


```shell
cd client
```
```shell
npm i
```
```shell
npm run dev
```

### Run server : 

> **!! Assuming you set up env variables like 'JAVA_HOME' and 'MAVEN_HOME' and installed mvn and java jdk correctly.**

- In `application.properties` set up :
  - datasource username, pwd, db url
- Use in terminal:
    ```shell
    cd server
    ```
    ```shell
    ./mvnw spring-boot:run
    ```
- run `import.sql` in `resources` folder for fake data in database
