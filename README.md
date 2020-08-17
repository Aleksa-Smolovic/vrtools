# Websocket

# Dependencies:

```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-messaging</artifactId>
    </dependency>
```

## Configuration:

Required config classes: `WebsocketConfiguration` where entry points are defined,
`WebsocketSecurityConfiguration` where authorization and security is defined for said entry points,
meaning all request for given endpoints must be authorized!

`resources\websocket-client\index.html` - Client app

## Entry points:

In `WebsocketConfiguration` defined three entry points:
`queue`: used for specific sending data to specific users (check useful links)
`send`: (custom) used for sending data through websockets (not necessary)
`topic`: standard topic entry point through which users are being subscribed.

In `WebsocketSecurityConfiguration` configured mentioned entry points to
be accessible only for authorized users (BearerToken).

## Functions:

`@MessageMapping` - entry point for sending data (like @PostMapping, @PutMapping for websockets).
Starts with already configured /send so it can be authorized.

`SimpMessageSendingOperations` - used for sending data to client. It can be used
to send to topic or specific user (check `WebsocketService` class).

## Notes:

- Check `ClientForwardController` class if there is null origin error.
  Instead of mapping:

      ```@GetMapping(value = {"/{path:[^\\.]*}"})```

it should be:

    @GetMapping(value = {"/{path:[^\\.]*}", "/{path:^(?!websocket).*}/**/{path:[^\\.]*}"})

- Check `JWTFilter` class in order to be able to use auth. By default class is not configured to support
  websocket bearer tokens, so check if following is covered:

      ```public static final String AUTHORIZATION_TOKEN = "access_token";```

Client sends bearer token using `access_tokenm={BearerToken}` on connect function. Mentioned `AUTHORIZATION_TOKEN` logic should be included in `JWTFilter` in order for bearer to work.
Check useful links for additional information.

## Useful links:

Stomp: https://stomp-js.github.io/stomp-websocket/codo/class/Client.html
JWTFilter:
Sending data to specific user: https://stackoverflow.com/questions/22367223/sending-message-to-specific-user-on-spring-websocket#answer-31577152
