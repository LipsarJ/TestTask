Настройка базы данных

    Шаг 1: Установите PostgreSQL
        Если PostgreSQL еще не установлен, загрузите и установите его с официального сайта.

    Шаг 2: Создайте базу данных
        Создайте базу данных с помощью SQL-скрипта (см. ниже). Скрипт создаст базу данных и пользователя, которые будут использоваться приложением.
        
        Скрипт для создания базы данных предоставлен в файле CreateDB.sql в корне проекта.

    Шаг 3: Настройте параметры соединения с БД
    Откройте файл application.properties и убедитесь, что параметры подключения к базе данных правильно настроены.
    
    
    spring.datasource.url=jdbc:postgresql://${DATABASE_HOST:localhost}:${DATABASE_PORT:5432}/${DATABASE_NAME:postgres}
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.datasource.username=${DATABASE_USER:postgres}
    spring.datasource.password=${DATABASE_PASSWORD:password}
    spring.jpa.hibernate.ddl-auto=validate
    spring.jpa.hibernate.show-sql=true
    spring.properties.hibernate.format_sql=true
    Вы также можете задать значения через переменные окружения (DATABASE_HOST, DATABASE_PORT, DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD).

Настройка приложения

    Шаг 1: Убедитесь, что у вас установлен JDK 17
        Убедитесь, что JDK версии 17 установлен на вашем компьютере. Проверить это можно с помощью команды:
        java -version
    Шаг 2: Установите Gradle
        Gradle используется для сборки проекта. Убедитесь, что Gradle установлен, или используйте встроенный Gradle Wrapper:
            ./gradlew build
    Шаг 3: Установите зависимости
        Используйте Gradle для загрузки всех необходимых библиотек:
            ./gradlew dependencies

Конфигурация build.gradle

    Файл build.gradle уже настроен для работы с проектом. Вот его основные элементы:

    Flyway: Настроен для миграции базы данных.
    MapStruct: Используется для преобразования DTO и сущностей.
    Lombok: Упрощает создание геттеров, сеттеров и конструкторов.
    PostgreSQL: Используется как база данных.
    Если хотите изменить настройки Flyway, параметры подключения указываются в блоке flyway:

        flyway {
        url = dbUrl
        user = dbUser
        password = dbPassword
        }

Использование Flyway для миграции

    Для создания таблиц без данных уже есть готовый файл миграции: V1__init.sql.
    Запустите миграцию командой:
      ./gradlew flywayMigrate

Сборка и запуск приложения

    Для сборки приложения выполните команду:
      ./gradlew build 
    Для запуска приложения:
      ./gradlew bootRun


Пример JSON файла находится в корне проекта: exported_orders.json.
