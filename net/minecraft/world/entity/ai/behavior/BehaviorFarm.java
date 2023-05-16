package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCrops;
import net.minecraft.world.level.block.BlockSoil;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BehaviorFarm extends Behavior<EntityVillager> {
   private static final int d = 200;
   public static final float c = 0.5F;
   @Nullable
   private BlockPosition e;
   private long f;
   private int g;
   private final List<BlockPosition> h = Lists.newArrayList();

   public BehaviorFarm() {
      super(ImmutableMap.of(MemoryModuleType.n, MemoryStatus.b, MemoryModuleType.m, MemoryStatus.b, MemoryModuleType.f, MemoryStatus.a));
   }

   protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
      if (!worldserver.W().b(GameRules.c)) {
         return false;
      } else if (entityvillager.gd().b() != VillagerProfession.g) {
         return false;
      } else {
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = entityvillager.dg().j();
         this.h.clear();

         for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
               for(int k = -1; k <= 1; ++k) {
                  blockposition_mutableblockposition.b(entityvillager.dl() + (double)i, entityvillager.dn() + (double)j, entityvillager.dr() + (double)k);
                  if (this.a(blockposition_mutableblockposition, worldserver)) {
                     this.h.add(new BlockPosition(blockposition_mutableblockposition));
                  }
               }
            }
         }

         this.e = this.a(worldserver);
         return this.e != null;
      }
   }

   @Nullable
   private BlockPosition a(WorldServer worldserver) {
      return this.h.isEmpty() ? null : this.h.get(worldserver.r_().a(this.h.size()));
   }

   private boolean a(BlockPosition blockposition, WorldServer worldserver) {
      IBlockData iblockdata = worldserver.a_(blockposition);
      Block block = iblockdata.b();
      Block block1 = worldserver.a_(blockposition.d()).b();
      return block instanceof BlockCrops && ((BlockCrops)block).h(iblockdata) || iblockdata.h() && block1 instanceof BlockSoil;
   }

   protected void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
      if (i > this.f && this.e != null) {
         entityvillager.dH().a(MemoryModuleType.n, new BehaviorTarget(this.e));
         entityvillager.dH().a(MemoryModuleType.m, new MemoryTarget(new BehaviorTarget(this.e), 0.5F, 1));
      }
   }

   protected void b(WorldServer worldserver, EntityVillager entityvillager, long i) {
      entityvillager.dH().b(MemoryModuleType.n);
      entityvillager.dH().b(MemoryModuleType.m);
      this.g = 0;
      this.f = i + 40L;
   }

   protected void c(WorldServer worldserver, EntityVillager entityvillager, long i) {
      if (this.e == null || this.e.a(entityvillager.de(), 1.0)) {
         if (this.e != null && i > this.f) {
            IBlockData iblockdata = worldserver.a_(this.e);
            Block block = iblockdata.b();
            Block block1 = worldserver.a_(this.e.d()).b();
            if (block instanceof BlockCrops
               && ((BlockCrops)block).h(iblockdata)
               && !CraftEventFactory.callEntityChangeBlockEvent(entityvillager, this.e, Blocks.a.o()).isCancelled()) {
               worldserver.a(this.e, true, entityvillager);
            }

            if (iblockdata.h() && block1 instanceof BlockSoil && entityvillager.gm()) {
               InventorySubcontainer inventorysubcontainer = entityvillager.w();

               for(int j = 0; j < inventorysubcontainer.b(); ++j) {
                  ItemStack itemstack = inventorysubcontainer.a(j);
                  boolean flag = false;
                  if (!itemstack.b()) {
                     IBlockData iblockdata1;
                     if (itemstack.a(Items.oD)) {
                        iblockdata1 = Blocks.cA.o();
                        flag = true;
                     } else if (itemstack.a(Items.ti)) {
                        iblockdata1 = Blocks.gt.o();
                        flag = true;
                     } else if (itemstack.a(Items.th)) {
                        iblockdata1 = Blocks.gs.o();
                        flag = true;
                     } else if (itemstack.a(Items.um)) {
                        iblockdata1 = Blocks.kA.o();
                        flag = true;
                     } else {
                        iblockdata1 = null;
                     }

                     if (iblockdata1 != null && !CraftEventFactory.callEntityChangeBlockEvent(entityvillager, this.e, iblockdata1).isCancelled()) {
                        worldserver.b(this.e, iblockdata1);
                        worldserver.a(GameEvent.i, this.e, GameEvent.a.a(entityvillager, iblockdata1));
                     } else {
                        flag = false;
                     }
                  }

                  if (flag) {
                     worldserver.a(null, (double)this.e.u(), (double)this.e.v(), (double)this.e.w(), SoundEffects.fe, SoundCategory.e, 1.0F, 1.0F);
                     itemstack.h(1);
                     if (itemstack.b()) {
                        inventorysubcontainer.a(j, ItemStack.b);
                     }
                     break;
                  }
               }
            }

            if (block instanceof BlockCrops && !((BlockCrops)block).h(iblockdata)) {
               this.h.remove(this.e);
               this.e = this.a(worldserver);
               if (this.e != null) {
                  this.f = i + 20L;
                  entityvillager.dH().a(MemoryModuleType.m, new MemoryTarget(new BehaviorTarget(this.e), 0.5F, 1));
                  entityvillager.dH().a(MemoryModuleType.n, new BehaviorTarget(this.e));
               }
            }
         }

         ++this.g;
      }
   }

   protected boolean d(WorldServer worldserver, EntityVillager entityvillager, long i) {
      return this.g < 200;
   }
}
