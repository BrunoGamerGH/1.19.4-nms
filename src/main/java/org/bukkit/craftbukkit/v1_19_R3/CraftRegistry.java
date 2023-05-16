package org.bukkit.craftbukkit.v1_19_R3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_19_R3.generator.strucutre.CraftStructure;
import org.bukkit.craftbukkit.v1_19_R3.generator.strucutre.CraftStructureType;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;

public class CraftRegistry<B extends Keyed, M> implements Registry<B> {
   private final Map<NamespacedKey, B> cache = new HashMap();
   private final IRegistry<M> minecraftRegistry;
   private final BiFunction<NamespacedKey, M, B> minecraftToBukkit;

   public static <B extends Keyed> Registry<?> createRegistry(Class<B> bukkitClass, IRegistryCustom registryHolder) {
      if (bukkitClass == Structure.class) {
         return new CraftRegistry<>(registryHolder.d(Registries.ax), CraftStructure::new);
      } else {
         return bukkitClass == StructureType.class ? new CraftRegistry<>(BuiltInRegistries.T, CraftStructureType::new) : null;
      }
   }

   public CraftRegistry(IRegistry<M> minecraftRegistry, BiFunction<NamespacedKey, M, B> minecraftToBukkit) {
      this.minecraftRegistry = minecraftRegistry;
      this.minecraftToBukkit = minecraftToBukkit;
   }

   public B get(NamespacedKey namespacedKey) {
      B cached = (B)this.cache.get(namespacedKey);
      if (cached != null) {
         return cached;
      } else {
         B bukkit = this.createBukkit(namespacedKey, this.minecraftRegistry.b(CraftNamespacedKey.toMinecraft(namespacedKey)).orElse((M)null));
         if (bukkit == null) {
            return null;
         } else {
            this.cache.put(namespacedKey, bukkit);
            return bukkit;
         }
      }
   }

   public Iterator<B> iterator() {
      return this.values().iterator();
   }

   public B createBukkit(NamespacedKey namespacedKey, M minecraft) {
      return (B)(minecraft == null ? null : this.minecraftToBukkit.apply(namespacedKey, minecraft));
   }

   public Stream<B> values() {
      return this.minecraftRegistry.e().stream().map(minecraftKey -> this.get(CraftNamespacedKey.fromMinecraft(minecraftKey)));
   }
}
