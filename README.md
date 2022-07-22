# Token And Message Service

## Service, выполняющий следующий функционал:
- Проверка клиента в базе по name и password
- Генерация, проверка наличия и валидация JWT токена
- Сохранение сообщений от клиента
- Вывод списка ранее сохраненных сообщений клиента

## Есть два способа запустить приложение: локально и с помощью Docker.

## Необходимые инструменты для запуска локально❗
1. `JDK 11` - для работы сервиса
2. `Maven` - для сборки проекта
3. `PostgreSQL` - для хранения данных

## Подготовка 🔨
Вам понадобится СУБД PostgreSQL. Отройте pgAdmin и создайте базу данных с названием "inside".

## Запуск проекта
1. Скопируйте проект к себе с помощью git clone.
2. В файле application.yaml добавьте данные для подключения к базе данных (имя пользователя, пароль и URL).
```java
    spring:
        datasource:
            driver-class-name: org.postgresql.Driver
            username: postgres - имя пользователя PostgreSQL
            password: admin - пароль пользователя PostgreSQL
            url: jdbc:postgresql://localhost:5432/inside - URL для подключения к базе даныых (добавьте порт и адрес, если у Вас другие)

        jpa:
            properties.hibernate:
            show_sql: true
            format_sql: true
            hbm2ddl.auto: validate
            open-in-view: false

        liquibase:
            liquibase-schema: public

        server:
            port: 9000 - порт

        jwt.token:
            secret: insidetesttaskantonnarizhnyjuniorjavadeveloper
            expired: 3600000
```
3. При первом запуске проекта Liquibase создаст все необходимые таблицы в базе данных и добавит в них данные.

### **Технологии, которые были использованы**:
* Java 11
* Spring Framework (Core, Validation, Boot, Web, Data JPA)
* PostgreSQL
* Maven
* Swagger
* Lombok
* Liquibase
* JWT