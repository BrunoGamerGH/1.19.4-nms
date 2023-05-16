package net.minecraft.world.level.storage.loot.parameters;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public class LootContextParameters {
   public static final LootContextParameter<Entity> a = a("this_entity");
   public static final LootContextParameter<EntityHuman> b = a("last_damage_player");
   public static final LootContextParameter<DamageSource> c = a("damage_source");
   public static final LootContextParameter<Entity> d = a("killer_entity");
   public static final LootContextParameter<Entity> e = a("direct_killer_entity");
   public static final LootContextParameter<Vec3D> f = a("origin");
   public static final LootContextParameter<IBlockData> g = a("block_state");
   public static final LootContextParameter<TileEntity> h = a("block_entity");
   public static final LootContextParameter<ItemStack> i = a("tool");
   public static final LootContextParameter<Float> j = a("explosion_radius");
   public static final LootContextParameter<Integer> LOOTING_MOD = new LootContextParameter<>(new MinecraftKey("bukkit:looting_mod"));

   private static <T> LootContextParameter<T> a(String s) {
      return new LootContextParameter<>(new MinecraftKey(s));
   }
}
