package org.frcteam4146.common.io.json;

import com.google.gson.*;
import java.lang.reflect.Type;
import org.frcteam4146.common.math.Rotation2;

public final class Rotation2JsonHandler
    implements JsonDeserializer<Rotation2>, JsonSerializer<Rotation2> {
  @Override
  public JsonElement serialize(Rotation2 src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toDegrees());
  }

  @Override
  public Rotation2 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    return Rotation2.fromDegrees(json.getAsDouble());
  }
}
