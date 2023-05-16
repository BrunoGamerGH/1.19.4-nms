package org.bukkit.craftbukkit.v1_19_R3.profile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.configuration.ConfigSerializationUtil;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

@SerializableAs("PlayerProfile")
public final class CraftPlayerProfile implements PlayerProfile {
   private final UUID uniqueId;
   private final String name;
   private final PropertyMap properties = new PropertyMap();
   private final CraftPlayerTextures textures = new CraftPlayerTextures(this);

   @Nonnull
   public static GameProfile validateSkullProfile(@Nonnull GameProfile gameProfile) {
      boolean isValidSkullProfile = gameProfile.getName() != null || gameProfile.getProperties().containsKey("textures");
      Preconditions.checkArgument(isValidSkullProfile, "The skull profile is missing a name or textures!");
      return gameProfile;
   }

   @Nullable
   public static Property getProperty(@Nonnull GameProfile profile, String propertyName) {
      return (Property)Iterables.getFirst(profile.getProperties().get(propertyName), null);
   }

   public CraftPlayerProfile(UUID uniqueId, String name) {
      Preconditions.checkArgument(uniqueId != null || !StringUtils.isBlank(name), "uniqueId is null or name is blank");
      this.uniqueId = uniqueId;
      this.name = name;
   }

   public CraftPlayerProfile(@Nonnull GameProfile gameProfile) {
      this(gameProfile.getId(), gameProfile.getName());
      this.properties.putAll(gameProfile.getProperties());
   }

   private CraftPlayerProfile(@Nonnull CraftPlayerProfile other) {
      this(other.uniqueId, other.name);
      this.properties.putAll(other.properties);
      this.textures.copyFrom(other.textures);
   }

   public UUID getUniqueId() {
      return this.uniqueId;
   }

   public String getName() {
      return this.name;
   }

   @Nullable
   Property getProperty(String propertyName) {
      return (Property)Iterables.getFirst(this.properties.get(propertyName), null);
   }

   void setProperty(String propertyName, @Nullable Property property) {
      this.removeProperty(propertyName);
      if (property != null) {
         this.properties.put(property.getName(), property);
      }
   }

   void removeProperty(String propertyName) {
      this.properties.removeAll(propertyName);
   }

   void rebuildDirtyProperties() {
      this.textures.rebuildPropertyIfDirty();
   }

   public CraftPlayerTextures getTextures() {
      return this.textures;
   }

   public void setTextures(@Nullable PlayerTextures textures) {
      if (textures == null) {
         this.textures.clear();
      } else {
         this.textures.copyFrom(textures);
      }
   }

   public boolean isComplete() {
      return this.uniqueId != null && this.name != null && !this.textures.isEmpty();
   }

   public CompletableFuture<PlayerProfile> update() {
      return CompletableFuture.supplyAsync(this::getUpdatedProfile, SystemUtils.f());
   }

   private CraftPlayerProfile getUpdatedProfile() {
      DedicatedServer server = ((CraftServer)Bukkit.getServer()).getServer();
      GameProfile profile = this.buildGameProfile();
      if (profile.getId() == null) {
         profile = (GameProfile)server.ap().a(profile.getName()).orElse(profile);
      }

      if (profile.getId() != null) {
         GameProfile newProfile = server.am().fillProfileProperties(profile, true);
         if (newProfile != null) {
            profile = newProfile;
         }
      }

      return new CraftPlayerProfile(profile);
   }

   @Nonnull
   public GameProfile buildGameProfile() {
      this.rebuildDirtyProperties();
      GameProfile profile = new GameProfile(this.uniqueId, this.name);
      profile.getProperties().putAll(this.properties);
      return profile;
   }

   @Override
   public String toString() {
      this.rebuildDirtyProperties();
      StringBuilder builder = new StringBuilder();
      builder.append("CraftPlayerProfile [uniqueId=");
      builder.append(this.uniqueId);
      builder.append(", name=");
      builder.append(this.name);
      builder.append(", properties=");
      builder.append(toString(this.properties));
      builder.append("]");
      return builder.toString();
   }

   private static String toString(@Nonnull PropertyMap propertyMap) {
      StringBuilder builder = new StringBuilder();
      builder.append("{");
      propertyMap.asMap().forEach((propertyName, properties) -> {
         builder.append(propertyName);
         builder.append("=");
         builder.append(properties.stream().map(CraftProfileProperty::toString).collect(Collectors.joining(",", "[", "]")));
      });
      builder.append("}");
      return builder.toString();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof CraftPlayerProfile)) {
         return false;
      } else {
         CraftPlayerProfile other = (CraftPlayerProfile)obj;
         if (!Objects.equals(this.uniqueId, other.uniqueId)) {
            return false;
         } else if (!Objects.equals(this.name, other.name)) {
            return false;
         } else {
            this.rebuildDirtyProperties();
            other.rebuildDirtyProperties();
            return equals(this.properties, other.properties);
         }
      }
   }

   private static boolean equals(@Nonnull PropertyMap propertyMap, @Nonnull PropertyMap other) {
      if (propertyMap.size() != other.size()) {
         return false;
      } else {
         Iterator<Property> iterator1 = propertyMap.values().iterator();
         Iterator<Property> iterator2 = other.values().iterator();

         while(iterator1.hasNext()) {
            if (!iterator2.hasNext()) {
               return false;
            }

            Property property1 = (Property)iterator1.next();
            Property property2 = (Property)iterator2.next();
            if (!CraftProfileProperty.equals(property1, property2)) {
               return false;
            }
         }

         return !iterator2.hasNext();
      }
   }

   @Override
   public int hashCode() {
      this.rebuildDirtyProperties();
      int result = 1;
      result = 31 * result + Objects.hashCode(this.uniqueId);
      result = 31 * result + Objects.hashCode(this.name);
      return 31 * result + hashCode(this.properties);
   }

   private static int hashCode(PropertyMap propertyMap) {
      int result = 1;

      for(Property property : propertyMap.values()) {
         result = 31 * result + CraftProfileProperty.hashCode(property);
      }

      return result;
   }

   public CraftPlayerProfile clone() {
      return new CraftPlayerProfile(this);
   }

   public Map<String, Object> serialize() {
      Map<String, Object> map = new LinkedHashMap<>();
      if (this.uniqueId != null) {
         map.put("uniqueId", this.uniqueId.toString());
      }

      if (this.name != null) {
         map.put("name", this.name);
      }

      this.rebuildDirtyProperties();
      if (!this.properties.isEmpty()) {
         List<Object> propertiesData = new ArrayList<>();
         this.properties.forEach((propertyName, property) -> propertiesData.add(CraftProfileProperty.serialize(property)));
         map.put("properties", propertiesData);
      }

      return map;
   }

   public static CraftPlayerProfile deserialize(Map<String, Object> map) {
      UUID uniqueId = ConfigSerializationUtil.getUuid(map, "uniqueId", true);
      String name = ConfigSerializationUtil.getString(map, "name", true);
      CraftPlayerProfile profile = new CraftPlayerProfile(uniqueId, name);
      if (map.containsKey("properties")) {
         for(Object propertyData : (List)map.get("properties")) {
            if (!(propertyData instanceof Map)) {
               throw new IllegalArgumentException("Property data (" + propertyData + ") is not a valid Map");
            }

            Property property = CraftProfileProperty.deserialize((Map<?, ?>)propertyData);
            profile.properties.put(property.getName(), property);
         }
      }

      return profile;
   }
}
