package org.bukkit.craftbukkit.v1_19_R3.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;

public class CraftItemTag extends CraftTag<Item, Material> {
   public CraftItemTag(IRegistry<Item> registry, TagKey<Item> tag) {
      super(registry, tag);
   }

   public boolean isTagged(Material item) {
      Item minecraft = CraftMagicNumbers.getItem(item);
      return minecraft == null ? false : minecraft.j().a(this.tag);
   }

   public Set<Material> getValues() {
      return this.getHandle().a().map(item -> CraftMagicNumbers.getMaterial((Item)item.a())).collect(Collectors.toUnmodifiableSet());
   }
}
