package ru.yarka.rasplibrary;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class RASPSessionTokenManager {

    private static final CopyOnWriteArraySet<String> tokenPool = new CopyOnWriteArraySet<>();

    public static String generateToken() {
        String token = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        tokenPool.add(token);
        return token;
    }

    public static boolean sessionExists(String token) {
        return tokenPool.contains(token);
    }

    public static void closeSession(String token) {
        tokenPool.remove(token);
    }
}
