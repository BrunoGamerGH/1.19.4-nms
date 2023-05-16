package org.bukkit.craftbukkit.v1_19_R3.util;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

@Deprecated
public final class CraftLegacy {
   private CraftLegacy() {
   }

   public static Material fromLegacy(Material material) {
      return material != null && material.isLegacy() ? org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacy(material) : material;
   }

   public static Material fromLegacy(MaterialData materialData) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacy(materialData);
   }

   public static Material[] modern_values() {
      Material[] values = Material.values();
      return Arrays.copyOfRange(values, 0, Material.LEGACY_AIR.ordinal());
   }

   public static int modern_ordinal(Material material) {
      if (material.isLegacy()) {
         throw new NoSuchFieldError("Legacy field ordinal: " + material);
      } else {
         return material.ordinal();
      }
   }
}
