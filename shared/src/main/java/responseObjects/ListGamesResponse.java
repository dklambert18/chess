package responseObjects;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public record ListGamesResponse(List<GameData> games) {
}
