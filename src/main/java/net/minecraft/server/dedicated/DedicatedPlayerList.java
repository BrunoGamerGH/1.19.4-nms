package net.minecraft.server.dedicated;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.WorldNBTStorage;
import org.slf4j.Logger;

public class DedicatedPlayerList extends PlayerList {
   private static final Logger a = LogUtils.getLogger();

   public DedicatedPlayerList(DedicatedServer var0, LayeredRegistryAccess<RegistryLayer> var1, WorldNBTStorage var2) {
      super(var0, var1, var2, var0.a().H);
      DedicatedServerProperties var3 = var0.a();
      this.a(var3.F);
      this.b(var3.G);
      super.a(var3.V.get());
      this.z();
      this.x();
      this.y();
      this.w();
      this.A();
      this.C();
      this.B();
      if (!this.i().b().exists()) {
         this.D();
      }
   }

   @Override
   public void a(boolean var0) {
      super.a(var0);
      this.b().i(var0);
   }

   @Override
   public void a(GameProfile var0) {
      super.a(var0);
      this.B();
   }

   @Override
   public void b(GameProfile var0) {
      super.b(var0);
      this.B();
   }

   @Override
   public void a() {
      this.C();
   }

   private void w() {
      try {
         this.g().e();
      } catch (IOException var2) {
         a.warn("Failed to save ip banlist: ", var2);
      }
   }

   private void x() {
      try {
         this.f().e();
      } catch (IOException var2) {
         a.warn("Failed to save user banlist: ", var2);
      }
   }

   private void y() {
      try {
         this.g().f();
      } catch (IOException var2) {
         a.warn("Failed to load ip banlist: ", var2);
      }
   }

   private void z() {
      try {
         this.f().f();
      } catch (IOException var2) {
         a.warn("Failed to load user banlist: ", var2);
      }
   }

   private void A() {
      try {
         this.k().f();
      } catch (Exception var2) {
         a.warn("Failed to load operators list: ", var2);
      }
   }

   private void B() {
      try {
         this.k().e();
      } catch (Exception var2) {
         a.warn("Failed to save operators list: ", var2);
      }
   }

   private void C() {
      try {
         this.i().f();
      } catch (Exception var2) {
         a.warn("Failed to load white-list: ", var2);
      }
   }

   private void D() {
      try {
         this.i().e();
      } catch (Exception var2) {
         a.warn("Failed to save white-list: ", var2);
      }
   }

   @Override
   public boolean c(GameProfile var0) {
      return !this.o() || this.f(var0) || this.i().a(var0);
   }

   public DedicatedServer b() {
      return (DedicatedServer)super.c();
   }

   @Override
   public boolean d(GameProfile var0) {
      return this.k().a(var0);
   }
}
