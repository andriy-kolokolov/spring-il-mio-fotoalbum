## Spring rest API + Vuejs simple photoalbum manager 

## Features
- Server - Authentication system and rest API protected by CORS and JWT token with expiration. Roles
- Client - Components render like navigation, buttons ecc.. based on auth status
- authenticated users can:
  - view list of all photos in 'Photos' page
  - send messages to other users using photo card action button 'message'
  - create (using file upload), read, update, delete, make visible/invisible their own photos.
  - if user role is 'super_admin' can update and delete all users photos from his 'Dashboard' page
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
