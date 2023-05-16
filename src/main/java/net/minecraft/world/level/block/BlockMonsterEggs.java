package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySilverfish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockMonsterEggs extends Block {
   private final Block a;
   private static final Map<Block, Block> b = Maps.newIdentityHashMap();
   private static final Map<IBlockData, IBlockData> c = Maps.newIdentityHashMap();
   private static final Map<IBlockData, IBlockData> d = Maps.newIdentityHashMap();

   public BlockMonsterEggs(Block block, BlockBase.Info blockbase_info) {
      super(blockbase_info.e(block.u() / 2.0F).f(0.75F));
      this.a = block;
      b.put(block, this);
   }

   public Block b() {
      return this.a;
   }

   public static boolean h(IBlockData iblockdata) {
      return b.containsKey(iblockdata.b());
   }

   private void a(WorldServer worldserver, BlockPosition blockposition) {
      EntitySilverfish entitysilverfish = EntityTypes.aI.a((World)worldserver);
      if (entitysilverfish != null) {
         entitysilverfish.b((double)blockposition.u() + 0.5, (double)blockposition.v(), (double)blockposition.w() + 0.5, 0.0F, 0.0F);
         worldserver.addFreshEntity(entitysilverfish, SpawnReason.SILVERFISH_BLOCK);
         entitysilverfish.M();
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      super.a(iblockdata, worldserver, blockposition, itemstack, flag);
      if (worldserver.W().b(GameRules.g) && EnchantmentManager.a(Enchantments.v, itemstack) == 0) {
         this.a(worldserver, blockposition);
      }
   }

   public static IBlockData n(IBlockData iblockdata) {
      return a(c, iblockdata, () -> b.get(iblockdata.b()).o());
   }

   public IBlockData o(IBlockData iblockdata) {
      return a(d, iblockdata, () -> this.b().o());
   }

   private static IBlockData a(Map<IBlockData, IBlockData> map, IBlockData iblockdata, Supplier<IBlockData> supplier) {
      return map.computeIfAbsent(iblockdata, iblockdata1 -> {
         IBlockData iblockdata2 = supplier.get();

         for(IBlockState iblockstate : iblockdata1.x()) {
            iblockdata2 = iblockdata2.b(iblockstate) ? iblockdata2.a(iblockstate, iblockdata1.c(iblockstate)) : iblockdata2;
         }

         return iblockdata2;
      });
   }
}
