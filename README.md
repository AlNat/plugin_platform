# Plugin Platform

![CI](https://github.com/AlNat/plugin_platform/workflows/CI/badge.svg)
![Docker-build](https://github.com/AlNat/plugin_platform/workflows/docker-build/badge.svg)

Платформа для запуска различных адаптеров в рамках одного платформенного приложения.
По факту является мини-шиной, с очень ограниченным функционалом.

API -- http://localhost/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config


## Мотивация

Proof-of-Concept для платформы запуска типовых плагинов (интеграций, задач и пр.) в рамках одного Java приложения.

Аналогичное можно сделать через микросервис на каждый плагин или система распределенных вычислений (к примеру Ignite).
Но микросервис по плагину при значительном их числе будет требовать много ресурсов или внешний шедулер, который должен их 
разворачивать перед работой и сворачивать после. Этого можно добиться через API K8S, но каждое приложение все равно будет требовать 
значительных ресурсов на веб-сервер и тд (например, при использовании Rabbit\Kafka для организации распределения запросов).
А Ignite или другие распределенные системы являются еще одной сложной платформой поверх которой придется разворачивать приложения.

Альтернативой можно назвать OSGi как способ создать плагины.


## HLD

Есть ядро (это приложение) и набор плагинов, которые реализуют интерфейс Plugin.
Каждый плагин реализует свою уникальную логику (обращение\задачи\...).
При вызове ядро пытается найти загруженные плагины в кеше, и если их там нет, пытается загрузить новый плагин.
Если плагин найден -- ему передается запрос и получается ответ.

Схему работы см. в workflow.puml


## Создание нового плагина

1) Реализовать новый плагин, которые реализует интерфейс `dev.alnat.plugin_platform.plugin.Plugin`
Например:
```java
package dev.somebody.plugins;

import dev.alnat.plugin_platform.plugin.Plugin;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;

public class SomePlugin implements Plugin {

    @Override
    public PluginResponse call(PluginRequest request) {
        return PluginResponse.builder()
                .response("Test")
                .build();
    }

}
```

2) Упаковать его в jar файл

3) Сконфигурировать платформу для его запуска через дополнение `application.yml` (на текущий момент).
Пример:
```yaml
custom:
    classes:
      new_plugin: "dev.somebody.plugins.SomePlugin"
```

4) Поместить плагин в директорию, на которую настроена платформа

5) Вызвать


## TODO

* Сделать Docker образ через CI по тегам

* Сделать загрузчик не просто с Autowired бинов, а каждый плагин делать бином и все его внутренние классы
  * Для этого нужно засунуть его в спринговый контекст и вручную создать все бины. По факту -- своя BeanFactory
  * См https://stackoverflow.com/questions/53961633/how-to-control-spring-context-initialization-errors 
    и https://stackoverflow.com/questions/27809838/how-to-instantiate-spring-managed-beans-at-runtime

* Вынести общие DTO (пакет `dev.alnat.plugin_platform.plugin`) в отдельный Maven репозиторий и подключать как зависимость

* Разнести плагины в отдельные подпроекты\модули Maven + настроить их CI процесс

* PluginConfig вынести из `application.yml` в отдельный загрузчик из реестра из БД\файла

* Сделать E2E тест со сборкой плагинов и запуском платформы с загрузкой этих плагинов и их проверкой
