package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.SheepDyeWoolEvent;

public class ItemDye extends Item {
   private static final Map<EnumColor, ItemDye> a = Maps.newEnumMap(EnumColor.class);
   private final EnumColor b;

   public ItemDye(EnumColor enumcolor, Item.Info item_info) {
      super(item_info);
      this.b = enumcolor;
      a.put(enumcolor, this);
   }

   @Override
   public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving, EnumHand enumhand) {
      if (entityliving instanceof EntitySheep entitysheep && entitysheep.bq() && !entitysheep.w() && entitysheep.r() != this.b) {
         entitysheep.H.a(entityhuman, entitysheep, SoundEffects.gB, SoundCategory.h, 1.0F, 1.0F);
         if (!entityhuman.H.B) {
            byte bColor = (byte)this.b.a();
            SheepDyeWoolEvent event = new SheepDyeWoolEvent(
               (Sheep)entitysheep.getBukkitEntity(), DyeColor.getByWoolData(bColor), (Player)entityhuman.getBukkitEntity()
            );
            entitysheep.H.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return EnumInteractionResult.d;
            }

            entitysheep.b(EnumColor.a(event.getColor().getWoolData()));
            itemstack.h(1);
         }

         return EnumInteractionResult.a(entityhuman.H.B);
      }

      return EnumInteractionResult.d;
   }

   public EnumColor d() {
      return this.b;
   }

   public static ItemDye a(EnumColor enumcolor) {
      return a.get(enumcolor);
   }
}
