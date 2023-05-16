package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemFireworks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BehaviorCelebrate extends Behavior<EntityVillager> {
   @Nullable
   private Raid c;

   public BehaviorCelebrate(int var0, int var1) {
      super(ImmutableMap.of(), var0, var1);
   }

   protected boolean a(WorldServer var0, EntityVillager var1) {
      BlockPosition var2 = var1.dg();
      this.c = var0.c(var2);
      return this.c != null && this.c.e() && BehaviorOutside.a(var0, var1, var2);
   }

   protected boolean a(WorldServer var0, EntityVillager var1, long var2) {
      return this.c != null && !this.c.d();
   }

   protected void b(WorldServer var0, EntityVillager var1, long var2) {
      this.c = null;
      var1.dH().a(var0.V(), var0.U());
   }

   protected void c(WorldServer var0, EntityVillager var1, long var2) {
      RandomSource var4 = var1.dZ();
      if (var4.a(100) == 0) {
         var1.fX();
      }

      if (var4.a(200) == 0 && BehaviorOutside.a(var0, var1, var1.dg())) {
         EnumColor var5 = SystemUtils.a(EnumColor.values(), var4);
         int var6 = var4.a(3);
         ItemStack var7 = this.a(var5, var6);
         EntityFireworks var8 = new EntityFireworks(var1.H, var1, var1.dl(), var1.dp(), var1.dr(), var7);
         var1.H.b(var8);
      }
   }

   private ItemStack a(EnumColor var0, int var1) {
      ItemStack var2 = new ItemStack(Items.tw, 1);
      ItemStack var3 = new ItemStack(Items.tx);
      NBTTagCompound var4 = var3.a("Explosion");
      List<Integer> var5 = Lists.newArrayList();
      var5.add(var0.f());
      var4.b("Colors", var5);
      var4.a("Type", (byte)ItemFireworks.EffectType.e.a());
      NBTTagCompound var6 = var2.a("Fireworks");
      NBTTagList var7 = new NBTTagList();
      NBTTagCompound var8 = var3.b("Explosion");
      if (var8 != null) {
         var7.add(var8);
      }

      var6.a("Flight", (byte)var1);
      if (!var7.isEmpty()) {
         var6.a("Explosions", var7);
      }

      return var2;
   }
}
