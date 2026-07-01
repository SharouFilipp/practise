# Платёжный валидатор
REST-сервис приёма и валидации платёжных запросов, разработанный в рамках банковского тестового задания.  
Сервис работает под управлением **WildFly 11** (совместим с JBoss EAP 7.1), принимает JSON‑запросы по защищённому протоколу HTTPS на порту **7443** и проверяет все обязательные поля, а также числовой формат суммы.

## Возможности
- Обработка POST-запросов с телом в формате JSON (12 полей платёжной транзакции).
- Все поля обязательны: пустые или отсутствующие ключи возвращают сообщение об ошибке с именем поля.
- Дополнительная проверка поля `amount` – допускаются только цифры (регулярное выражение `\d+`).
- Единый формат ответа:
  - Успех: `{"Error":"0","MSG":"OK"}`
  - Ошибка: `{"Error":"1","MSG":"fields <имя поля> is empty"}`
- HTTP‑статус ответа всегда **200 OK**.

## Требования
- **JDK 8** (для сборки и запуска сервера; код компилируется под Java 6)
- **Apache Maven 3.2+**
- **WildFly 11** (или более поздняя версия, совместимая с JBoss EAP 7.1)

> **Примечание о совместимости:**  
> Исходный код написан в стиле Java 6 (без лямбд, Stream API и diamond‑оператора). Параметры компиляции `source/target = 1.6` гарантируют бинарную совместимость с Java 6. При необходимости сервис может быть развёрнут на JBoss AS 7.1 с JDK 6. В текущей конфигурации используется WildFly 11 и JDK 8.

## Подготовка окружения

### 1. Установка JDK 8
Рекомендуется бесплатный дистрибутив **Eclipse Temurin** (Adoptium).  
Скачать без регистрации: [https://adoptium.net/download/](https://adoptium.net/download/)  
Выберите **Java 8 (LTS)**, **Windows**, **x64**, загрузите установщик и выполните установку. Запомните путь, например `C:\Program Files\Eclipse Adoptium\jdk-8.0.432.6-hotspot`.

После установки откройте **Командную строку (cmd)** и настройте переменные окружения:
```cmd
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-8.0.432.6-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
```
Проверьте:
``` cmd
java -version
javac -version
```
### 2. Установка Maven
Скачайте Maven 3.2.5
Распакуйте архив, например, в C:\apache-maven-3.2.5.
Добавьте путь к исполняемым файлам в ту же сессию cmd:
```cmd
set PATH=C:\apache-maven-3.2.5\bin;%PATH%
```
Проверьте:
``` cmd
mvn -version
```

### 3.Установка WildFly 11
Скачайте WildFly 11, и распакуйте его например `C:\wildfly-11.0.0.Final`
## Настройка HTTPS на порту 7443

   1. Откройте файл конфигурации сервера:  
   `C:\wildfly-11.0.0.Final\standalone\configuration\standalone.xml`
   2. Найдите подсистему `<subsystem xmlns="urn:jboss:domain:undertow:4.0">` и внутри `<server name="default-server">` после строки `<http-listener name="default" ...>` добавьте HTTPS‑коннектор:

   ```xml
   <https-listener name="https" socket-binding="https" security-realm="ApplicationRealm"/>
   ```
   3. Найдите группу сокетов `<socket-binding-group>` и укажите порт:

   ```xml
   <socket-binding name="https" port="${jboss.https.port:7443}"/>
   ```
   4. Сохраните файл. Самоподписанный сертификат будет автоматически сгенерирован WildFly при первом запуске (файл application.keystore появится в папке конфигурации). Для учебных целей этого достаточно.

## Сборка проекта

Клонируйте репозиторий или скопируйте исходные файлы в локальную папку.  
Откройте `cmd`, перейдите в папку проекта (где лежит `pom.xml`) и выполните сборку:

```cmd
mvn clean package
```

## Развёртывание

  1. Убедитесь, что сервер WildFly **запущен** (откройте новое окно `cmd`, перейдите в `C:\wildfly-11.0.0.Final\bin` и выполните `standalone.bat`).

  2. Скопируйте WAR‑файл в папку `deployments`:

     ```cmd
     сopy C:\path\to\payment-validator\target\payment-validator-1.0-SNAPSHOT.war C:\wildfly-11.0.0.Final\standalone\deployments\
     ```
   3.В логах сервера появится сообщение:
  
   ```cmd
   WFLYSRV0010: Deployed "payment-validator-1.0-SNAPSHOT.war"
   ```
  4. Сервис будет доступен по адресу:
   ```cmd
   https://localhost:7443/payment-validator-1.0-SNAPSHOT/api/payment
   ```
   > Если вы переименуете WAR‑файл в `payment-validator.war`, контекст приложения изменится на `/payment-validator`.

## Тестирование

Используйте утилиту `curl` (установленную отдельно или доступную в Windows 10+).  
Флаг `-k` отключает проверку самоподписанного сертификата.  
Флаг `-v` включает подробный вывод, чтобы увидеть HTTP‑статус `200 OK`.

Все примеры выполняются в **cmd** (командная строка), а не в PowerShell.

### 1. Успешный запрос (все поля заполнены, amount – цифры)

```cmd
curl -k -v -X POST https://localhost:7443/payment-validator-1.0-SNAPSHOT/api/payment -H "Content-Type: application/json" -d "{\"request_id\":\"1\",\"loan_id\":\"2\",\"terminal_id\":\"3\",\"merchant_id\":\"4\",\"nspc_trans_id\":\"5\",\"amount\":\"100\",\"commission_amount\":\"0\",\"payment_type\":\"1\",\"card_type\":\"VISA\",\"authorization_code\":\"abc\",\"payment_date\":\"01.01.2023\",\"operation_number\":\"100\"}"
```
Ожидаемый ответ:

```text
< HTTP/1.1 200 OK
...
{"Error":"0","MSG":"OK"}
```
## Технологии

- Java EE 7 / JAX-RS 2.0 (RESTEasy)
- Apache Maven 3.2.5
- WildFly 11 (Undertow, HTTPS на порту 7443)
- Jackson (автоматическая сериализация JSON)
- Самоподписанный сертификат (автоматическая генерация)
