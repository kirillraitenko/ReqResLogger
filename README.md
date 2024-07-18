# ReqResLogger Starter

ReqResLogger Starter - это Spring Boot Starter для логирования HTTP-запросов и ответов. Он позволяет легко добавлять логирование всех входящих и исходящих HTTP-запросов и ответов в вашем приложении Spring Boot.

## Функциональность

- Логирование метода запроса (GET, POST и т.д.).
- Логирование URL запроса.
- Логирование заголовков запроса и ответа.
- Логирование кода ответа.
- Логирование времени обработки запроса.

## Установка

Просто откройте проект и запустите **mvn clean install**

Для использования `ReqResLogger` добавьте следующую зависимость в ваш `pom.xml`:

```xml
<dependency>
    <groupId>com.t1</groupId>
    <artifactId>reqres-logger-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Примеры контроллеров:

```java
@RestController
@RequestMapping("/example")
@Slf4j
public class ExampleController {

    @GetMapping("/resp")
    public ResponseEntity<String> testResp() {
        ThreadUtils.sleep(100);
        return ResponseEntity.ok().body("Success request");
    }

    @GetMapping("/map")
    public Map<String, String> getMap() {
        ThreadUtils.sleep(100);
        return Map.of("param1", "val1", "param2", "val2");
    }

    @LoggingDisabled
    @GetMapping("/map_disabled")
    public Map<String, String> getMapDisabled() {
        return Map.of("param1", "val1", "param2", "val2");
    }
}
```

Можно отключить логирования для конкретного метода **@LoggingDisabled** пример:
```java
@LoggingDisabled
@GetMapping("/map_disabled")
public Map<String, String> getMapDisabled() {
return Map.of("param1", "val1", "param2", "val2");
}
```

### Пример выключенного логирования для контроллера @LoggingDisabled:
```java
@RestController
@RequestMapping("/disabled")
@Slf4j
@LoggingDisabled
public class DisabledController {

    @GetMapping("/resp")
    public ResponseEntity<String> testResp() {
        ThreadUtils.sleep(100);
        return ResponseEntity.ok().body("Request successful. Logging - disabled !");
    }

    @GetMapping("/map")
    public Map<String, String> testMap() {
        ThreadUtils.sleep(100);
        return Map.of("param1", "val1", "param2", "val2");
    }
}
```

#Пример логов

```logging
2024-07-18 23:33:30 - Request: 
	HTTP Method: GET;
	 Request URL: http://localhost:8080/example/resp;
	 Request Headers: [(host: localhost:8080)(connection: keep-alive)(sec-ch-ua: "Chromium";v="124", "YaBrowser";v="24.6", "Not-A.Brand";v="99", "Yowser";v="2.5")(sec-ch-ua-mobile: ?0)(sec-ch-ua-platform: "Windows")(upgrade-insecure-requests: 1)(user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 YaBrowser/24.6.0.0 Safari/537.36)(accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7)(sec-fetch-site: none)(sec-fetch-mode: navigate)(sec-fetch-user: ?1)(sec-fetch-dest: document)(accept-encoding: gzip, deflate, br, zstd)(accept-language: ru,en;q=0.9)]

2024-07-18 23:33:30 - Response: 
	Status: 200;
	 Body : Success request;
	 Headers: []

2024-07-18 23:33:30 - Processing Time: 107 ms

```