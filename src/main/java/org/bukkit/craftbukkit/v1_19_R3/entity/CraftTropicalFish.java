package org.bukkit.craftbukkit.v1_19_R3.entity;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.entity.animal.EntityTropicalFish;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.TropicalFish.Pattern;

public class CraftTropicalFish extends CraftFish implements TropicalFish {
   public CraftTropicalFish(CraftServer server, EntityTropicalFish entity) {
      super(server, entity);
   }

   public EntityTropicalFish getHandle() {
      return (EntityTropicalFish)this.entity;
   }

   @Override
   public String toString() {
      return "CraftTropicalFish";
   }

   @Override
   public EntityType getType() {
      return EntityType.TROPICAL_FISH;
   }

   public DyeColor getPatternColor() {
      return getPatternColor(this.getHandle().ge());
   }

   public void setPatternColor(DyeColor color) {
      this.getHandle().u(getData(color, this.getBodyColor(), this.getPattern()));
   }

   public DyeColor getBodyColor() {
      return getBodyColor(this.getHandle().ge());
   }

   public void setBodyColor(DyeColor color) {
      this.getHandle().u(getData(this.getPatternColor(), color, this.getPattern()));
   }

   public Pattern getPattern() {
      return getPattern(this.getHandle().ge());
   }

   public void setPattern(Pattern pattern) {
      this.getHandle().u(getData(this.getPatternColor(), this.getBodyColor(), pattern));
   }

   public static int getData(DyeColor patternColor, DyeColor bodyColor, Pattern type) {
      return patternColor.getWoolData() << 24 | bodyColor.getWoolData() << 16 | CraftTropicalFish.CraftPattern.values()[type.ordinal()].getDataValue();
   }

   public static DyeColor getPatternColor(int data) {
      return DyeColor.getByWoolData((byte)(data >> 24 & 0xFF));
   }

   public static DyeColor getBodyColor(int data) {
      return DyeColor.getByWoolData((byte)(data >> 16 & 0xFF));
   }

   public static Pattern getPattern(int data) {
      return CraftTropicalFish.CraftPattern.fromData(data & 65535);
   }

   public static enum CraftPattern {
      KOB(0, false),
      SUNSTREAK(1, false),
      SNOOPER(2, false),
      DASHER(3, false),
      BRINELY(4, false),
      SPOTTY(5, false),
      FLOPPER(0, true),
      STRIPEY(1, true),
      GLITTER(2, true),
      BLOCKFISH(3, true),
      BETTY(4, true),
      CLAYFISH(5, true);

      private final int variant;
      private final boolean large;
      private static final Map<Integer, Pattern> BY_DATA = new HashMap<>();

      static {
         CraftTropicalFish.CraftPattern[] var3;
         for(CraftTropicalFish.CraftPattern type : var3 = values()) {
            BY_DATA.put(type.getDataValue(), Pattern.values()[type.ordinal()]);
         }
      }

      public static Pattern fromData(int data) {
         return (Pattern)BY_DATA.get(data);
      }

      private CraftPattern(int variant, boolean large) {
         this.variant = variant;
         this.large = large;
      }

      public int getDataValue() {
         return this.variant << 8 | (this.large ? 1 : 0);
      }
   }
}
