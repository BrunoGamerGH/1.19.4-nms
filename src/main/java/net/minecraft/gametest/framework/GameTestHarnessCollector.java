package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class GameTestHarnessCollector {
   private static final char a = ' ';
   private static final char b = '_';
   private static final char c = '+';
   private static final char d = 'x';
   private static final char e = 'X';
   private final Collection<GameTestHarnessInfo> f = Lists.newArrayList();
   @Nullable
   private final Collection<GameTestHarnessListener> g = Lists.newArrayList();

   public GameTestHarnessCollector() {
   }

   public GameTestHarnessCollector(Collection<GameTestHarnessInfo> var0) {
      this.f.addAll(var0);
   }

   public void a(GameTestHarnessInfo var0) {
      this.f.add(var0);
      this.g.forEach(var0::a);
   }

   public void a(GameTestHarnessListener var0) {
      this.g.add(var0);
      this.f.forEach(var1x -> var1x.a(var0));
   }

   public void a(final Consumer<GameTestHarnessInfo> var0) {
      this.a(new GameTestHarnessListener() {
         @Override
         public void a(GameTestHarnessInfo var0x) {
         }

         @Override
         public void b(GameTestHarnessInfo var0x) {
         }

         @Override
         public void c(GameTestHarnessInfo var0x) {
            var0.accept(var0);
         }
      });
   }

   public int a() {
      return (int)this.f.stream().filter(GameTestHarnessInfo::i).filter(GameTestHarnessInfo::r).count();
   }

   public int b() {
      return (int)this.f.stream().filter(GameTestHarnessInfo::i).filter(GameTestHarnessInfo::s).count();
   }

   public int c() {
      return (int)this.f.stream().filter(GameTestHarnessInfo::k).count();
   }

   public boolean d() {
      return this.a() > 0;
   }

   public boolean e() {
      return this.b() > 0;
   }

   public Collection<GameTestHarnessInfo> f() {
      return this.f.stream().filter(GameTestHarnessInfo::i).filter(GameTestHarnessInfo::r).collect(Collectors.toList());
   }

   public Collection<GameTestHarnessInfo> g() {
      return this.f.stream().filter(GameTestHarnessInfo::i).filter(GameTestHarnessInfo::s).collect(Collectors.toList());
   }

   public int h() {
      return this.f.size();
   }

   public boolean i() {
      return this.c() == this.h();
   }

   public String j() {
      StringBuffer var0 = new StringBuffer();
      var0.append('[');
      this.f.forEach(var1x -> {
         if (!var1x.j()) {
            var0.append(' ');
         } else if (var1x.h()) {
            var0.append('+');
         } else if (var1x.i()) {
            var0.append((char)(var1x.r() ? 'X' : 'x'));
         } else {
            var0.append('_');
         }
      });
      var0.append(']');
      return var0.toString();
   }

   @Override
   public String toString() {
      return this.j();
   }
}
