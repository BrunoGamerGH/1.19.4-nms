package net.minecraft.world.level.block.entity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.Services;
import net.minecraft.server.players.UserCache;
import net.minecraft.util.UtilColor;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntitySkull extends TileEntity {
   public static final String a = "SkullOwner";
   public static final String b = "note_block_sound";
   @Nullable
   private static UserCache c;
   @Nullable
   private static MinecraftSessionService d;
   @Nullable
   private static Executor e;
   @Nullable
   public GameProfile f;
   @Nullable
   public MinecraftKey g;
   private int h;
   private boolean i;

   public TileEntitySkull(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.p, var0, var1);
   }

   public static void a(Services var0, Executor var1) {
      c = var0.d();
      d = var0.a();
      e = var1;
   }

   public static void c() {
      c = null;
      d = null;
      e = null;
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      if (this.f != null) {
         NBTTagCompound var1 = new NBTTagCompound();
         GameProfileSerializer.a(var1, this.f);
         var0.a("SkullOwner", var1);
      }

      if (this.g != null) {
         var0.a("note_block_sound", this.g.toString());
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      if (var0.b("SkullOwner", 10)) {
         this.a(GameProfileSerializer.a(var0.p("SkullOwner")));
      } else if (var0.b("ExtraType", 8)) {
         String var1 = var0.l("ExtraType");
         if (!UtilColor.b(var1)) {
            this.a(new GameProfile(null, var1));
         }
      }

      if (var0.b("note_block_sound", 8)) {
         this.g = MinecraftKey.a(var0.l("note_block_sound"));
      }
   }

   public static void a(World var0, BlockPosition var1, IBlockData var2, TileEntitySkull var3) {
      if (var0.r(var1)) {
         var3.i = true;
         ++var3.h;
      } else {
         var3.i = false;
      }
   }

   public float a(float var0) {
      return this.i ? (float)this.h + var0 : (float)this.h;
   }

   @Nullable
   public GameProfile d() {
      return this.f;
   }

   @Nullable
   public MinecraftKey f() {
      return this.g;
   }

   public PacketPlayOutTileEntityData g() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   public void a(@Nullable GameProfile var0) {
      synchronized(this) {
         this.f = var0;
      }

      this.i();
   }

   private void i() {
      a(this.f, var0 -> {
         this.f = var0;
         this.e();
      });
   }

   public static void a(@Nullable GameProfile var0, Consumer<GameProfile> var1) {
      if (var0 != null && !UtilColor.b(var0.getName()) && (!var0.isComplete() || !var0.getProperties().containsKey("textures")) && c != null && d != null) {
         c.a(var0.getName(), var2 -> SystemUtils.f().execute(() -> SystemUtils.a(var2, var1xxx -> {
                  Property var2xx = (Property)Iterables.getFirst(var1xxx.getProperties().get("textures"), null);
                  if (var2xx == null) {
                     MinecraftSessionService var3 = d;
                     if (var3 == null) {
                        return;
                     }

                     var1xxx = var3.fillProfileProperties(var1xxx, true);
                  }

                  GameProfile var3 = var1xxx;
                  Executor var4 = e;
                  if (var4 != null) {
                     var4.execute(() -> {
                        UserCache var2xxx = c;
                        if (var2xxx != null) {
                           var2xxx.a(var3);
                           var1.accept(var3);
                        }
                     });
                  }
               }, () -> {
                  Executor var2xx = e;
                  if (var2xx != null) {
                     var2xx.execute(() -> var1.accept(var0));
                  }
               })));
      } else {
         var1.accept(var0);
      }
   }
}
