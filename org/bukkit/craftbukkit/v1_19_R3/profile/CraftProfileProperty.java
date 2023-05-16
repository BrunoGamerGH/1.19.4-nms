package org.bukkit.craftbukkit.v1_19_R3.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.bukkit.craftbukkit.v1_19_R3.configuration.ConfigSerializationUtil;

final class CraftProfileProperty {
   private static final PublicKey PUBLIC_KEY;

   static {
      try {
         X509EncodedKeySpec spec = new X509EncodedKeySpec(
            IOUtils.toByteArray(YggdrasilMinecraftSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der"))
         );
         PUBLIC_KEY = KeyFactory.getInstance("RSA").generatePublic(spec);
      } catch (Exception var1) {
         throw new Error("Could not find yggdrasil_session_pubkey.der! This indicates a bug.");
      }
   }

   public static boolean hasValidSignature(@Nonnull Property property) {
      return property.hasSignature() && property.isSignatureValid(PUBLIC_KEY);
   }

   @Nullable
   private static String decodeBase64(@Nonnull String encoded) {
      try {
         return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   @Nullable
   public static JsonObject decodePropertyValue(@Nonnull String encodedPropertyValue) {
      String json = decodeBase64(encodedPropertyValue);
      if (json == null) {
         return null;
      } else {
         try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return !jsonElement.isJsonObject() ? null : jsonElement.getAsJsonObject();
         } catch (JsonParseException var3) {
            return null;
         }
      }
   }

   @Nonnull
   public static String encodePropertyValue(@Nonnull JsonObject propertyValue, @Nonnull CraftProfileProperty.JsonFormatter formatter) {
      String json = formatter.format(propertyValue);
      return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
   }

   @Nonnull
   public static String toString(@Nonnull Property property) {
      StringBuilder builder = new StringBuilder();
      builder.append("{");
      builder.append("name=");
      builder.append(property.getName());
      builder.append(", value=");
      builder.append(property.getValue());
      builder.append(", signature=");
      builder.append(property.getSignature());
      builder.append("}");
      return builder.toString();
   }

   public static int hashCode(@Nonnull Property property) {
      int result = 1;
      result = 31 * result + Objects.hashCode(property.getName());
      result = 31 * result + Objects.hashCode(property.getValue());
      return 31 * result + Objects.hashCode(property.getSignature());
   }

   public static boolean equals(@Nullable Property property, @Nullable Property other) {
      if (property != null && other != null) {
         if (!Objects.equals(property.getValue(), other.getValue())) {
            return false;
         } else if (!Objects.equals(property.getName(), other.getName())) {
            return false;
         } else {
            return Objects.equals(property.getSignature(), other.getSignature());
         }
      } else {
         return property == other;
      }
   }

   public static Map<String, Object> serialize(@Nonnull Property property) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("name", property.getName());
      map.put("value", property.getValue());
      if (property.hasSignature()) {
         map.put("signature", property.getSignature());
      }

      return map;
   }

   public static Property deserialize(@Nonnull Map<?, ?> map) {
      String name = ConfigSerializationUtil.getString(map, "name", false);
      String value = ConfigSerializationUtil.getString(map, "value", false);
      String signature = ConfigSerializationUtil.getString(map, "signature", true);
      return new Property(name, value, signature);
   }

   private CraftProfileProperty() {
   }

   public interface JsonFormatter {
      CraftProfileProperty.JsonFormatter COMPACT = new CraftProfileProperty.JsonFormatter() {
         private final Gson gson = new GsonBuilder().create();

         @Override
         public String format(JsonElement jsonElement) {
            return this.gson.toJson(jsonElement);
         }
      };

      String format(JsonElement var1);
   }
}
