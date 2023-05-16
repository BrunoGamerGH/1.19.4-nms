package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.EnumBlockSupport;
import org.bukkit.block.BlockSupport;

public final class CraftBlockSupport {
   private CraftBlockSupport() {
   }

   public static BlockSupport toBukkit(EnumBlockSupport support) {
      return switch(support) {
         case a -> BlockSupport.FULL;
         case b -> BlockSupport.CENTER;
         case c -> BlockSupport.RIGID;
         default -> throw new IllegalArgumentException("Unsupported EnumBlockSupport type: " + support + ". This is a bug.");
      };
   }

   public static EnumBlockSupport toNMS(BlockSupport support) {
      return switch(support) {
         case FULL -> EnumBlockSupport.a;
         case CENTER -> EnumBlockSupport.b;
         case RIGID -> EnumBlockSupport.c;
         default -> throw new IllegalArgumentException("Unsupported BlockSupport type: " + support + ". This is a bug.");
      };
   }
}
