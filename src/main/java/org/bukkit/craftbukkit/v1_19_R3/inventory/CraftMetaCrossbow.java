package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.item.ItemArrow;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaCrossbow extends CraftMetaItem implements CrossbowMeta {
   static final CraftMetaItem.ItemMetaKey CHARGED = new CraftMetaItem.ItemMetaKey("Charged", "charged");
   static final CraftMetaItem.ItemMetaKey CHARGED_PROJECTILES = new CraftMetaItem.ItemMetaKey("ChargedProjectiles", "charged-projectiles");
   private boolean charged;
   private List<ItemStack> chargedProjectiles;

   CraftMetaCrossbow(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaCrossbow) {
         CraftMetaCrossbow crossbow = (CraftMetaCrossbow)meta;
         this.charged = crossbow.charged;
         if (crossbow.hasChargedProjectiles()) {
            this.chargedProjectiles = new ArrayList(crossbow.chargedProjectiles);
         }
      }
   }

   CraftMetaCrossbow(NBTTagCompound tag) {
      super(tag);
      this.charged = tag.q(CHARGED.NBT);
      if (tag.b(CHARGED_PROJECTILES.NBT, 9)) {
         NBTTagList list = tag.c(CHARGED_PROJECTILES.NBT, 10);
         if (list != null && !list.isEmpty()) {
            this.chargedProjectiles = new ArrayList();

            for(int i = 0; i < list.size(); ++i) {
               NBTTagCompound nbttagcompound1 = list.a(i);
               this.chargedProjectiles.add(CraftItemStack.asCraftMirror(net.minecraft.world.item.ItemStack.a(nbttagcompound1)));
            }
         }
      }
   }

   CraftMetaCrossbow(Map<String, Object> map) {
      super(map);
      Boolean charged = CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, CHARGED.BUKKIT, true);
      if (charged != null) {
         this.charged = charged;
      }

      Iterable<?> projectiles = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CHARGED_PROJECTILES.BUKKIT, true);
      if (projectiles != null) {
         for(Object stack : projectiles) {
            if (stack instanceof ItemStack) {
               this.addChargedProjectile((ItemStack)stack);
            }
         }
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      tag.a(CHARGED.NBT, this.charged);
      if (this.hasChargedProjectiles()) {
         NBTTagList list = new NBTTagList();

         for(ItemStack item : this.chargedProjectiles) {
            NBTTagCompound saved = new NBTTagCompound();
            CraftItemStack.asNMSCopy(item).b(saved);
            list.add(saved);
         }

         tag.a(CHARGED_PROJECTILES.NBT, list);
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.CROSSBOW;
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isCrossbowEmpty();
   }

   boolean isCrossbowEmpty() {
      return !this.hasChargedProjectiles();
   }

   public boolean hasChargedProjectiles() {
      return this.chargedProjectiles != null;
   }

   public List<ItemStack> getChargedProjectiles() {
      return this.chargedProjectiles == null ? ImmutableList.of() : ImmutableList.copyOf(this.chargedProjectiles);
   }

   public void setChargedProjectiles(List<ItemStack> projectiles) {
      this.chargedProjectiles = null;
      this.charged = false;
      if (projectiles != null) {
         for(ItemStack i : projectiles) {
            this.addChargedProjectile(i);
         }
      }
   }

   public void addChargedProjectile(ItemStack item) {
      Preconditions.checkArgument(item != null, "item");
      Preconditions.checkArgument(
         item.getType() == Material.FIREWORK_ROCKET || CraftMagicNumbers.getItem(item.getType()) instanceof ItemArrow,
         "Item %s is not an arrow or firework rocket",
         item
      );
      if (this.chargedProjectiles == null) {
         this.chargedProjectiles = new ArrayList();
      }

      this.charged = true;
      this.chargedProjectiles.add(item);
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaCrossbow)) {
         return true;
      } else {
         CraftMetaCrossbow that = (CraftMetaCrossbow)meta;
         return this.charged == that.charged
            && (
               this.hasChargedProjectiles()
                  ? that.hasChargedProjectiles() && this.chargedProjectiles.equals(that.chargedProjectiles)
                  : !that.hasChargedProjectiles()
            );
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaCrossbow || this.isCrossbowEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasChargedProjectiles()) {
         hash = 61 * hash + (this.charged ? 1 : 0);
         hash = 61 * hash + this.chargedProjectiles.hashCode();
      }

      return original != hash ? CraftMetaCrossbow.class.hashCode() ^ hash : hash;
   }

   public CraftMetaCrossbow clone() {
      return (CraftMetaCrossbow)super.clone();
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      builder.put(CHARGED.BUKKIT, this.charged);
      if (this.hasChargedProjectiles()) {
         builder.put(CHARGED_PROJECTILES.BUKKIT, this.chargedProjectiles);
      }

      return builder;
   }
}
