## My Photoalbum

## How to run application ?

Clone repository using:
```shell
git clone
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
