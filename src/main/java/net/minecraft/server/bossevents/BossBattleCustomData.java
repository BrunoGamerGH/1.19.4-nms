package net.minecraft.server.bossevents;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;

public class BossBattleCustomData {
   private final Map<MinecraftKey, BossBattleCustom> a = Maps.newHashMap();

   @Nullable
   public BossBattleCustom a(MinecraftKey var0) {
      return this.a.get(var0);
   }

   public BossBattleCustom a(MinecraftKey var0, IChatBaseComponent var1) {
      BossBattleCustom var2 = new BossBattleCustom(var0, var1);
      this.a.put(var0, var2);
      return var2;
   }

   public void a(BossBattleCustom var0) {
      this.a.remove(var0.a());
   }

   public Collection<MinecraftKey> a() {
      return this.a.keySet();
   }

   public Collection<BossBattleCustom> b() {
      return this.a.values();
   }

   public NBTTagCompound c() {
      NBTTagCompound var0 = new NBTTagCompound();

      for(BossBattleCustom var2 : this.a.values()) {
         var0.a(var2.a().toString(), var2.f());
      }

      return var0;
   }

   public void a(NBTTagCompound var0) {
      for(String var2 : var0.e()) {
         MinecraftKey var3 = new MinecraftKey(var2);
         this.a.put(var3, BossBattleCustom.a(var0.p(var2), var3));
      }
   }

   public void a(EntityPlayer var0) {
      for(BossBattleCustom var2 : this.a.values()) {
         var2.c(var0);
      }
   }

   public void b(EntityPlayer var0) {
      for(BossBattleCustom var2 : this.a.values()) {
         var2.d(var0);
      }
   }
}
