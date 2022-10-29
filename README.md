Pull request: https://github.com/bannovdaniil/java-explore-with-me/pull/1

# _REST API для проекта "Explore With Me"_

### _Язык реализации Java_

В рамках дипломного проекта разработано REST API для приложения ExploreWithMe (англ. «исследуй со мной»). Оно
предоставляет
возможность
делиться информацией об интересных событиях и помогать найти компанию для участия в них.

### **Инструкция по развертыванию проекта:**

1. [Скачать данный репозиторий](https://github.com/bannovdaniil/java-explore-with-me)
2. mvn clean package
3. mvn install
4. docker-compose build
5. docker-compose up -d --force-recreate
6. основной сервис доступен по адресу: http://localhost:8080
7. сервис статистики доступен по адресу: http://localhost:9090

### _Приложение включает в себя сервисы:_

- GateWay (разрабатывается отдельно)
  - Проверяет права пользователей
  - Передает запросы на остальные микросервисы в зависимости прав
- Основной сервис — содержит всё необходимое для работы.
  - Просмотр событий без авторизации;
  - Возможность создания и управления категориями;
  - События и работа с ними - создание, модерация;
  - Запросы пользователей на участие в событии - запрос, подтверждение, отклонение.
  - Создание и управление подборками.
- Сервис статистики — хранит количество просмотров и позволяет делать различные выборки для анализа работы приложения.
  - Отдельный сервис для сбора статистики;

### _Описание сервисов:_

#### _Основной сервис, выделенный порт: 8080_

- **Публичный** (_доступен для всех пользователей_)
  - API для работы с событиями
  - API для работы с категориями
- **Приватный** (_доступен только для зарегистрированных пользователей_)
  - API для работы с событиями
  - API для работы с запросами текущего пользователя на участие в событиях
- **Административный** (_доступен только для администратора проекта_)
  - API для работы с событиями
  - API для работы с категориями
  - API для работы с пользователями
  - API для работы с подборками событий

#### Сервис статистики, выделенный порт: 9090

- **Административный** (_доступен только для администратора проекта_)
  - API для работы со статистикой посещений

### _Спецификация REST API swagger_

- [Основной сервис](https://raw.githubusercontent.com/bannovdaniil/java-explore-with-me/develop/ewm-main-service-spec.json)
- [Сервис статистики](https://raw.githubusercontent.com/bannovdaniil/java-explore-with-me/develop/ewm-stats-service-spec.json)

### _Postman тесты для сервисов:_

- [Основной сервис](https://raw.githubusercontent.com/bannovdaniil/java-explore-with-me/develop/postman/ewm-main-service.json)
- [Сервис статистики](https://raw.githubusercontent.com/bannovdaniil/java-explore-with-me/develop/postman/ewm-stat-service.json)

### _Схема Архитектуры проекта_

![Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/bannovdaniil/java-explore-with-me/develop/uml/project.puml?new)

### _Схема Базы данных проекта_

![Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/bannovdaniil/java-explore-with-me/develop/uml/db-ewm.puml?new)

![](https://raw.githubusercontent.com/bannovdaniil/java-explore-with-me/develop/uml/db-ewm-image.png)
