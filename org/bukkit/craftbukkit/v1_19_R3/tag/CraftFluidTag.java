package org.bukkit.craftbukkit.v1_19_R3.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.FluidType;
import org.bukkit.Fluid;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;

public class CraftFluidTag extends CraftTag<FluidType, Fluid> {
   public CraftFluidTag(IRegistry<FluidType> registry, TagKey<FluidType> tag) {
      super(registry, tag);
   }

   public boolean isTagged(Fluid fluid) {
      return CraftMagicNumbers.getFluid(fluid).a(this.tag);
   }

   public Set<Fluid> getValues() {
      return this.getHandle().a().map(fluid -> CraftMagicNumbers.getFluid((FluidType)fluid.a())).collect(Collectors.toUnmodifiableSet());
   }
}
