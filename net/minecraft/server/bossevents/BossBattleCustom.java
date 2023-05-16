package net.minecraft.server.bossevents;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.BossBattleServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.BossBattle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.craftbukkit.v1_19_R3.boss.CraftKeyedBossbar;

public class BossBattleCustom extends BossBattleServer {
   private final MinecraftKey h;
   private final Set<UUID> i = Sets.newHashSet();
   private int j;
   private int k = 100;
   private KeyedBossBar bossBar;

   public KeyedBossBar getBukkitEntity() {
      if (this.bossBar == null) {
         this.bossBar = new CraftKeyedBossbar(this);
      }

      return this.bossBar;
   }

   public BossBattleCustom(MinecraftKey minecraftkey, IChatBaseComponent ichatbasecomponent) {
      super(ichatbasecomponent, BossBattle.BarColor.g, BossBattle.BarStyle.a);
      this.h = minecraftkey;
      this.a(0.0F);
   }

   public MinecraftKey a() {
      return this.h;
   }

   @Override
   public void a(EntityPlayer entityplayer) {
      super.a(entityplayer);
      this.i.add(entityplayer.cs());
   }

   public void a(UUID uuid) {
      this.i.add(uuid);
   }

   @Override
   public void b(EntityPlayer entityplayer) {
      super.b(entityplayer);
      this.i.remove(entityplayer.cs());
   }

   @Override
   public void b() {
      super.b();
      this.i.clear();
   }

   public int c() {
      return this.j;
   }

   public int d() {
      return this.k;
   }

   public void a(int i) {
      this.j = i;
      this.a(MathHelper.a((float)i / (float)this.k, 0.0F, 1.0F));
   }

   public void b(int i) {
      this.k = i;
      this.a(MathHelper.a((float)this.j / (float)i, 0.0F, 1.0F));
   }

   public final IChatBaseComponent e() {
      return ChatComponentUtils.a(this.j())
         .a(
            chatmodifier -> chatmodifier.a(this.l().a())
                  .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.b(this.a().toString())))
                  .a(this.a().toString())
         );
   }

   public boolean a(Collection<EntityPlayer> collection) {
      Set<UUID> set = Sets.newHashSet();
      Set<EntityPlayer> set1 = Sets.newHashSet();

      for(UUID uuid : this.i) {
         boolean flag = false;

         for(EntityPlayer entityplayer : collection) {
            if (entityplayer.cs().equals(uuid)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            set.add(uuid);
         }
      }

      for(EntityPlayer entityplayer1 : collection) {
         boolean flag = false;

         for(UUID uuid1 : this.i) {
            if (entityplayer1.cs().equals(uuid1)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            set1.add(entityplayer1);
         }
      }

      for(UUID uuid : set) {
         for(EntityPlayer entityplayer2 : this.h()) {
            if (entityplayer2.cs().equals(uuid)) {
               this.b(entityplayer2);
               break;
            }
         }

         this.i.remove(uuid);
      }

      for(EntityPlayer entityplayer1 : set1) {
         this.a(entityplayer1);
      }

      return !set.isEmpty() || !set1.isEmpty();
   }

   public NBTTagCompound f() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.a("Name", IChatBaseComponent.ChatSerializer.a(this.a));
      nbttagcompound.a("Visible", this.g());
      nbttagcompound.a("Value", this.j);
      nbttagcompound.a("Max", this.k);
      nbttagcompound.a("Color", this.l().b());
      nbttagcompound.a("Overlay", this.m().a());
      nbttagcompound.a("DarkenScreen", this.n());
      nbttagcompound.a("PlayBossMusic", this.o());
      nbttagcompound.a("CreateWorldFog", this.p());
      NBTTagList nbttaglist = new NBTTagList();

      for(UUID uuid : this.i) {
         nbttaglist.add(GameProfileSerializer.a(uuid));
      }

      nbttagcompound.a("Players", nbttaglist);
      return nbttagcompound;
   }

   public static BossBattleCustom a(NBTTagCompound nbttagcompound, MinecraftKey minecraftkey) {
      BossBattleCustom bossbattlecustom = new BossBattleCustom(minecraftkey, IChatBaseComponent.ChatSerializer.a(nbttagcompound.l("Name")));
      bossbattlecustom.d(nbttagcompound.q("Visible"));
      bossbattlecustom.a(nbttagcompound.h("Value"));
      bossbattlecustom.b(nbttagcompound.h("Max"));
      bossbattlecustom.a(BossBattle.BarColor.a(nbttagcompound.l("Color")));
      bossbattlecustom.a(BossBattle.BarStyle.a(nbttagcompound.l("Overlay")));
      bossbattlecustom.a(nbttagcompound.q("DarkenScreen"));
      bossbattlecustom.b(nbttagcompound.q("PlayBossMusic"));
      bossbattlecustom.c(nbttagcompound.q("CreateWorldFog"));
      NBTTagList nbttaglist = nbttagcompound.c("Players", 11);

      for(int i = 0; i < nbttaglist.size(); ++i) {
         bossbattlecustom.a(GameProfileSerializer.a(nbttaglist.k(i)));
      }

      return bossbattlecustom;
   }

   public void c(EntityPlayer entityplayer) {
      if (this.i.contains(entityplayer.cs())) {
         this.a(entityplayer);
      }
   }

   public void d(EntityPlayer entityplayer) {
      super.b(entityplayer);
   }
}
