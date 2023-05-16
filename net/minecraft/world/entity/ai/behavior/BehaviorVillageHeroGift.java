package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class BehaviorVillageHeroGift extends Behavior<EntityVillager> {
   private static final int c = 5;
   private static final int d = 600;
   private static final int e = 6600;
   private static final int f = 20;
   private static final Map<VillagerProfession, MinecraftKey> g = SystemUtils.a(Maps.newHashMap(), var0 -> {
      var0.put(VillagerProfession.c, LootTables.an);
      var0.put(VillagerProfession.d, LootTables.ao);
      var0.put(VillagerProfession.e, LootTables.ap);
      var0.put(VillagerProfession.f, LootTables.aq);
      var0.put(VillagerProfession.g, LootTables.ar);
      var0.put(VillagerProfession.h, LootTables.as);
      var0.put(VillagerProfession.i, LootTables.at);
      var0.put(VillagerProfession.j, LootTables.au);
      var0.put(VillagerProfession.k, LootTables.av);
      var0.put(VillagerProfession.l, LootTables.aw);
      var0.put(VillagerProfession.n, LootTables.ax);
      var0.put(VillagerProfession.o, LootTables.ay);
      var0.put(VillagerProfession.p, LootTables.az);
   });
   private static final float h = 0.5F;
   private int i = 600;
   private boolean j;
   private long k;

   public BehaviorVillageHeroGift(int var0) {
      super(
         ImmutableMap.of(
            MemoryModuleType.m, MemoryStatus.c, MemoryModuleType.n, MemoryStatus.c, MemoryModuleType.q, MemoryStatus.c, MemoryModuleType.k, MemoryStatus.a
         ),
         var0
      );
   }

   protected boolean a(WorldServer var0, EntityVillager var1) {
      if (!this.b(var1)) {
         return false;
      } else if (this.i > 0) {
         --this.i;
         return false;
      } else {
         return true;
      }
   }

   protected void a(WorldServer var0, EntityVillager var1, long var2) {
      this.j = false;
      this.k = var2;
      EntityHuman var4 = this.c(var1).get();
      var1.dH().a(MemoryModuleType.q, var4);
      BehaviorUtil.a(var1, var4);
   }

   protected boolean b(WorldServer var0, EntityVillager var1, long var2) {
      return this.b(var1) && !this.j;
   }

   protected void c(WorldServer var0, EntityVillager var1, long var2) {
      EntityHuman var4 = this.c(var1).get();
      BehaviorUtil.a(var1, var4);
      if (this.a(var1, var4)) {
         if (var2 - this.k > 20L) {
            this.a(var1, (EntityLiving)var4);
            this.j = true;
         }
      } else {
         BehaviorUtil.a(var1, var4, 0.5F, 5);
      }
   }

   protected void d(WorldServer var0, EntityVillager var1, long var2) {
      this.i = a(var0);
      var1.dH().b(MemoryModuleType.q);
      var1.dH().b(MemoryModuleType.m);
      var1.dH().b(MemoryModuleType.n);
   }

   private void a(EntityVillager var0, EntityLiving var1) {
      for(ItemStack var4 : this.a(var0)) {
         BehaviorUtil.a(var0, var4, var1.de());
      }
   }

   private List<ItemStack> a(EntityVillager var0) {
      if (var0.y_()) {
         return ImmutableList.of(new ItemStack(Items.cO));
      } else {
         VillagerProfession var1 = var0.gd().b();
         if (g.containsKey(var1)) {
            LootTable var2 = var0.H.n().aH().a(g.get(var1));
            LootTableInfo.Builder var3 = new LootTableInfo.Builder((WorldServer)var0.H)
               .a(LootContextParameters.f, var0.de())
               .a(LootContextParameters.a, var0)
               .a(var0.dZ());
            return var2.a(var3.a(LootContextParameterSets.h));
         } else {
            return ImmutableList.of(new ItemStack(Items.oD));
         }
      }
   }

   private boolean b(EntityVillager var0) {
      return this.c(var0).isPresent();
   }

   private Optional<EntityHuman> c(EntityVillager var0) {
      return var0.dH().c(MemoryModuleType.k).filter(this::a);
   }

   private boolean a(EntityHuman var0) {
      return var0.a(MobEffects.F);
   }

   private boolean a(EntityVillager var0, EntityHuman var1) {
      BlockPosition var2 = var1.dg();
      BlockPosition var3 = var0.dg();
      return var3.a(var2, 5.0);
   }

   private static int a(WorldServer var0) {
      return 600 + var0.z.a(6001);
   }
}
