package net.minecraft.world.level.storage;

import java.util.UUID;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.timers.CustomFunctionCallbackTimerQueue;

public class SecondaryWorldData implements IWorldDataServer {
   private final SaveData a;
   private final IWorldDataServer b;

   public SecondaryWorldData(SaveData var0, IWorldDataServer var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public int a() {
      return this.b.a();
   }

   @Override
   public int b() {
      return this.b.b();
   }

   @Override
   public int c() {
      return this.b.c();
   }

   @Override
   public float d() {
      return this.b.d();
   }

   @Override
   public long e() {
      return this.b.e();
   }

   @Override
   public long f() {
      return this.b.f();
   }

   @Override
   public String g() {
      return this.a.g();
   }

   @Override
   public int h() {
      return this.b.h();
   }

   @Override
   public void a(int var0) {
   }

   @Override
   public boolean i() {
      return this.b.i();
   }

   @Override
   public int j() {
      return this.b.j();
   }

   @Override
   public boolean k() {
      return this.b.k();
   }

   @Override
   public int l() {
      return this.b.l();
   }

   @Override
   public EnumGamemode m() {
      return this.a.m();
   }

   @Override
   public void b(int var0) {
   }

   @Override
   public void c(int var0) {
   }

   @Override
   public void d(int var0) {
   }

   @Override
   public void a(float var0) {
   }

   @Override
   public void a(long var0) {
   }

   @Override
   public void b(long var0) {
   }

   @Override
   public void a(BlockPosition var0, float var1) {
   }

   @Override
   public void a(boolean var0) {
   }

   @Override
   public void e(int var0) {
   }

   @Override
   public void b(boolean var0) {
   }

   @Override
   public void f(int var0) {
   }

   @Override
   public void a(EnumGamemode var0) {
   }

   @Override
   public boolean n() {
      return this.a.n();
   }

   @Override
   public boolean o() {
      return this.a.o();
   }

   @Override
   public boolean p() {
      return this.b.p();
   }

   @Override
   public void c(boolean var0) {
   }

   @Override
   public GameRules q() {
      return this.a.q();
   }

   @Override
   public WorldBorder.c r() {
      return this.b.r();
   }

   @Override
   public void a(WorldBorder.c var0) {
   }

   @Override
   public EnumDifficulty s() {
      return this.a.s();
   }

   @Override
   public boolean t() {
      return this.a.t();
   }

   @Override
   public CustomFunctionCallbackTimerQueue<MinecraftServer> u() {
      return this.b.u();
   }

   @Override
   public int v() {
      return 0;
   }

   @Override
   public void g(int var0) {
   }

   @Override
   public int w() {
      return 0;
   }

   @Override
   public void h(int var0) {
   }

   @Override
   public UUID x() {
      return null;
   }

   @Override
   public void a(UUID var0) {
   }

   @Override
   public void a(CrashReportSystemDetails var0, LevelHeightAccessor var1) {
      var0.a("Derived", true);
      this.b.a(var0, var1);
   }
}
