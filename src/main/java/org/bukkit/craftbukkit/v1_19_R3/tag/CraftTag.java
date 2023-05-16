package org.bukkit.craftbukkit.v1_19_R3.tag;

import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.tags.TagKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;

public abstract class CraftTag<N, B extends Keyed> implements Tag<B> {
   protected final IRegistry<N> registry;
   protected final TagKey<N> tag;
   private HolderSet.Named<N> handle;

   public CraftTag(IRegistry<N> registry, TagKey<N> tag) {
      this.registry = registry;
      this.tag = tag;
      this.handle = registry.b(this.tag).orElseThrow();
   }

   protected HolderSet.Named<N> getHandle() {
      return this.handle;
   }

   public NamespacedKey getKey() {
      return CraftNamespacedKey.fromMinecraft(this.tag.b());
   }
}
