package com.substring.auth.auth_app_backend.helpers;

import java.util.UUID;

public class userHelper {
    public static UUID parseUUID(String uuid) {
        return UUID.fromString(uuid);
    }
}
