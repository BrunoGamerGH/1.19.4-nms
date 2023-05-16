package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.FireworkEffectMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaCharge extends CraftMetaItem implements FireworkEffectMeta {
   static final CraftMetaItem.ItemMetaKey EXPLOSION = new CraftMetaItem.ItemMetaKey("Explosion", "firework-effect");
   private FireworkEffect effect;

   CraftMetaCharge(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaCharge) {
         this.effect = ((CraftMetaCharge)meta).effect;
      }
   }

   CraftMetaCharge(Map<String, Object> map) {
      super(map);
      this.setEffect(CraftMetaItem.SerializableMeta.getObject(FireworkEffect.class, map, EXPLOSION.BUKKIT, true));
   }

   CraftMetaCharge(NBTTagCompound tag) {
      super(tag);
      if (tag.e(EXPLOSION.NBT)) {
         try {
            this.effect = CraftMetaFirework.getEffect(tag.p(EXPLOSION.NBT));
         } catch (IllegalArgumentException var3) {
         }
      }
   }

   public void setEffect(FireworkEffect effect) {
      this.effect = effect;
   }

   public boolean hasEffect() {
      return this.effect != null;
   }

   public FireworkEffect getEffect() {
      return this.effect;
   }

   @Override
   void applyToItem(NBTTagCompound itemTag) {
      super.applyToItem(itemTag);
      if (this.hasEffect()) {
         itemTag.a(EXPLOSION.NBT, CraftMetaFirework.getExplosion(this.effect));
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.FIREWORK_STAR;
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && !this.hasChargeMeta();
   }

   boolean hasChargeMeta() {
      return this.hasEffect();
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaCharge)) {
         return true;
      } else {
         CraftMetaCharge that = (CraftMetaCharge)meta;
         return this.hasEffect() ? that.hasEffect() && this.effect.equals(that.effect) : !that.hasEffect();
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaCharge || !this.hasChargeMeta());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasEffect()) {
         hash = 61 * hash + this.effect.hashCode();
      }

      return hash != original ? CraftMetaCharge.class.hashCode() ^ hash : hash;
   }

   public CraftMetaCharge clone() {
      return (CraftMetaCharge)super.clone();
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasEffect()) {
         builder.put(EXPLOSION.BUKKIT, this.effect);
      }

      return builder;
   }
}
