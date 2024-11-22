# Wallet app (JavaCode test task).

Стек используемых технологий:
Java 17, Spring boot 3 (Data Jpa, Web, Cache, Validation), Liquibase, PostgreSQL, Mapstruct, Testcontainers, JUnit

## Инструкция по запуску.

### Клонирование проекта.

Для клонирования проекта с Github необходимо ввести команду:

`git clone https://github.com/Falltren/wallet.git`

### Создание docker образа.

Для создания docker образа приложения (включая все необходимые сервисы) необходимо ввести команду:

`docker build -t wallet-app:0.0.1 .`

### Запуск приложения в docker.

Для запуска docker контейнеров необходимо ввести команду:

`docker compose up -d`

### Использование приложения.

В приложении предусмотрено два эндпоинта:

Для получения баланса кошелька
`/api/v1/wallets/{WALLET_UUID}`

Для изменения баланса кошелька
`/api/v1/wallet`

Для проверки работы эндпоинтов добавлен тестовый кошелек с UUID `1ae92e91-7f45-4410-9f60-88db2eb4418a`

