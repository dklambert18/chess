package responseObjects;

import java.util.UUID;

public record RegisterResponse(String username, String authToken) {
}
