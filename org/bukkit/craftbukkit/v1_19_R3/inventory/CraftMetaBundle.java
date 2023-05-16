package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaBundle extends CraftMetaItem implements BundleMeta {
   static final CraftMetaItem.ItemMetaKey ITEMS = new CraftMetaItem.ItemMetaKey("Items", "items");
   private List<ItemStack> items;

   CraftMetaBundle(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaBundle) {
         CraftMetaBundle bundle = (CraftMetaBundle)meta;
         if (bundle.hasItems()) {
            this.items = new ArrayList(bundle.items);
         }
      }
   }

   CraftMetaBundle(NBTTagCompound tag) {
      super(tag);
      if (tag.b(ITEMS.NBT, 9)) {
         NBTTagList list = tag.c(ITEMS.NBT, 10);
         if (list != null && !list.isEmpty()) {
            this.items = new ArrayList();

            for(int i = 0; i < list.size(); ++i) {
               NBTTagCompound nbttagcompound1 = list.a(i);
               ItemStack itemStack = CraftItemStack.asCraftMirror(net.minecraft.world.item.ItemStack.a(nbttagcompound1));
               if (!itemStack.getType().isAir()) {
                  this.addItem(itemStack);
               }
            }
         }
      }
   }

   CraftMetaBundle(Map<String, Object> map) {
      super(map);
      Iterable<?> items = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, ITEMS.BUKKIT, true);
      if (items != null) {
         for(Object stack : items) {
            ItemStack itemStack;
            if (stack instanceof ItemStack && (itemStack = (ItemStack)stack) == (ItemStack)stack && !itemStack.getType().isAir()) {
               this.addItem(itemStack);
            }
         }
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      if (this.hasItems()) {
         NBTTagList list = new NBTTagList();

         for(ItemStack item : this.items) {
            NBTTagCompound saved = new NBTTagCompound();
            CraftItemStack.asNMSCopy(item).b(saved);
            list.add(saved);
         }

         tag.a(ITEMS.NBT, list);
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.BUNDLE;
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isBundleEmpty();
   }

   boolean isBundleEmpty() {
      return !this.hasItems();
   }

   public boolean hasItems() {
      return this.items != null && !this.items.isEmpty();
   }

   public List<ItemStack> getItems() {
      return this.items == null ? ImmutableList.of() : ImmutableList.copyOf(this.items);
   }

   public void setItems(List<ItemStack> items) {
      this.items = null;
      if (items != null) {
         for(ItemStack i : items) {
            this.addItem(i);
         }
      }
   }

   public void addItem(ItemStack item) {
      Preconditions.checkArgument(item != null && !item.getType().isAir(), "item is null or air");
      if (this.items == null) {
         this.items = new ArrayList();
      }

      this.items.add(item);
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaBundle)) {
         return true;
      } else {
         CraftMetaBundle that = (CraftMetaBundle)meta;
         return this.hasItems() ? that.hasItems() && this.items.equals(that.items) : !that.hasItems();
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaBundle || this.isBundleEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasItems()) {
         hash = 61 * hash + this.items.hashCode();
      }

      return original != hash ? CraftMetaBundle.class.hashCode() ^ hash : hash;
   }

   public CraftMetaBundle clone() {
      return (CraftMetaBundle)super.clone();
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasItems()) {
         builder.put(ITEMS.BUKKIT, this.items);
      }

      return builder;
   }
}
