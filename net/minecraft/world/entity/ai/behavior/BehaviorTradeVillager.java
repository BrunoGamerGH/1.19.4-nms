package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BehaviorTradeVillager extends Behavior<EntityVillager> {
   private static final int c = 5;
   private static final float d = 0.5F;
   private Set<Item> e = ImmutableSet.of();

   public BehaviorTradeVillager() {
      super(ImmutableMap.of(MemoryModuleType.q, MemoryStatus.a, MemoryModuleType.h, MemoryStatus.a));
   }

   protected boolean a(WorldServer var0, EntityVillager var1) {
      return BehaviorUtil.a(var1.dH(), MemoryModuleType.q, EntityTypes.bf);
   }

   protected boolean a(WorldServer var0, EntityVillager var1, long var2) {
      return this.a(var0, var1);
   }

   protected void b(WorldServer var0, EntityVillager var1, long var2) {
      EntityVillager var4 = (EntityVillager)var1.dH().c(MemoryModuleType.q).get();
      BehaviorUtil.a(var1, var4, 0.5F);
      this.e = a(var1, var4);
   }

   protected void c(WorldServer var0, EntityVillager var1, long var2) {
      EntityVillager var4 = (EntityVillager)var1.dH().c(MemoryModuleType.q).get();
      if (!(var1.f(var4) > 5.0)) {
         BehaviorUtil.a(var1, var4, 0.5F);
         var1.a(var0, var4, var2);
         if (var1.gk() && (var1.gd().b() == VillagerProfession.g || var4.gl())) {
            a(var1, EntityVillager.bV.keySet(), var4);
         }

         if (var4.gd().b() == VillagerProfession.g && var1.w().a_(Items.oE) > Items.oE.l() / 2) {
            a(var1, ImmutableSet.of(Items.oE), var4);
         }

         if (!this.e.isEmpty() && var1.w().a(this.e)) {
            a(var1, this.e, var4);
         }
      }
   }

   protected void d(WorldServer var0, EntityVillager var1, long var2) {
      var1.dH().b(MemoryModuleType.q);
   }

   private static Set<Item> a(EntityVillager var0, EntityVillager var1) {
      ImmutableSet<Item> var2 = var1.gd().b().d();
      ImmutableSet<Item> var3 = var0.gd().b().d();
      return var2.stream().filter(var1x -> !var3.contains(var1x)).collect(Collectors.toSet());
   }

   private static void a(EntityVillager var0, Set<Item> var1, EntityLiving var2) {
      InventorySubcontainer var3 = var0.w();
      ItemStack var4 = ItemStack.b;
      int var5 = 0;

      while(var5 < var3.b()) {
         ItemStack var6;
         Item var7;
         int var8;
         label28: {
            var6 = var3.a(var5);
            if (!var6.b()) {
               var7 = var6.c();
               if (var1.contains(var7)) {
                  if (var6.K() > var6.f() / 2) {
                     var8 = var6.K() / 2;
                     break label28;
                  }

                  if (var6.K() > 24) {
                     var8 = var6.K() - 24;
                     break label28;
                  }
               }
            }

            ++var5;
            continue;
         }

         var6.h(var8);
         var4 = new ItemStack(var7, var8);
         break;
      }

      if (!var4.b()) {
         BehaviorUtil.a(var0, var4, var2.de());
      }
   }
}
