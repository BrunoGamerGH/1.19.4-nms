package org.bukkit.craftbukkit.v1_19_R3;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang.Validate;
import org.bukkit.Axis;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.potion.Potion;

public class CraftEffect {
   public static <T> int getDataValue(Effect effect, T data) {
      return switch(effect) {
         case RECORD_PLAY -> {
            Validate.isTrue(data == Material.AIR || ((Material)data).isRecord(), "Invalid record type!");
            yield Item.a(CraftMagicNumbers.getItem((Material)data));
         }
         case SMOKE -> {
            switch((BlockFace)data) {
               case NORTH:
                  yield 2;
               case EAST:
                  yield 5;
               case SOUTH:
                  yield 3;
               case WEST:
                  yield 4;
               case UP:
                  yield 1;
               case DOWN:
               case NORTH_EAST:
               case NORTH_WEST:
               case SOUTH_EAST:
               case SOUTH_WEST:
               case SELF:
                  yield 0;
               case WEST_NORTH_WEST:
               case NORTH_NORTH_WEST:
               case NORTH_NORTH_EAST:
               case EAST_NORTH_EAST:
               case EAST_SOUTH_EAST:
               case SOUTH_SOUTH_EAST:
               case SOUTH_SOUTH_WEST:
               case WEST_SOUTH_WEST:
               default:
                  throw new IllegalArgumentException("Bad smoke direction!");
            }
         }
         case STEP_SOUND -> {
            Validate.isTrue(((Material)data).isBlock(), "Material is not a block!");
            yield Block.i(CraftMagicNumbers.getBlock((Material)data).o());
         }
         case POTION_BREAK -> ((Potion)data).toDamageValue() & 63;
         case INSTANT_POTION_BREAK -> ((Color)data).asRGB();
         case VILLAGER_PLANT_GROW -> (Integer)data;
         case COMPOSTER_FILL_ATTEMPT -> (Boolean)data ? 1 : 0;
         case BONE_MEAL_USE -> (Integer)data;
         case ELECTRIC_SPARK -> {
            if (data == null) {
               yield -1;
            } else {
               switch((Axis)data) {
                  case X:
                     yield 0;
                  case Y:
                     yield 1;
                  case Z:
                     yield 2;
                  default:
                     throw new IllegalArgumentException("Bad electric spark axis!");
               }
            }
         }
         default -> 0;
      };
   }
}
