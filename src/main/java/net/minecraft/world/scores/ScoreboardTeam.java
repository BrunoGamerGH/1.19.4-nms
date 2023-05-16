package net.minecraft.world.scores;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;

public class ScoreboardTeam extends ScoreboardTeamBase {
   private static final int a = 0;
   private static final int b = 1;
   private final Scoreboard c;
   private final String d;
   private final Set<String> e = Sets.newHashSet();
   private IChatBaseComponent f;
   private IChatBaseComponent g = CommonComponents.a;
   private IChatBaseComponent h = CommonComponents.a;
   private boolean i = true;
   private boolean j = true;
   private ScoreboardTeamBase.EnumNameTagVisibility k = ScoreboardTeamBase.EnumNameTagVisibility.a;
   private ScoreboardTeamBase.EnumNameTagVisibility l = ScoreboardTeamBase.EnumNameTagVisibility.a;
   private EnumChatFormat m = EnumChatFormat.v;
   private ScoreboardTeamBase.EnumTeamPush n = ScoreboardTeamBase.EnumTeamPush.a;
   private final ChatModifier o;

   public ScoreboardTeam(Scoreboard var0, String var1) {
      this.c = var0;
      this.d = var1;
      this.f = IChatBaseComponent.b(var1);
      this.o = ChatModifier.a.a(var1).a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.b(var1)));
   }

   public Scoreboard a() {
      return this.c;
   }

   @Override
   public String b() {
      return this.d;
   }

   public IChatBaseComponent c() {
      return this.f;
   }

   public IChatMutableComponent d() {
      IChatMutableComponent var0 = ChatComponentUtils.a((IChatBaseComponent)this.f.e().c(this.o));
      EnumChatFormat var1 = this.n();
      if (var1 != EnumChatFormat.v) {
         var0.a(var1);
      }

      return var0;
   }

   public void a(IChatBaseComponent var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.f = var0;
         this.c.b(this);
      }
   }

   public void b(@Nullable IChatBaseComponent var0) {
      this.g = var0 == null ? CommonComponents.a : var0;
      this.c.b(this);
   }

   public IChatBaseComponent e() {
      return this.g;
   }

   public void c(@Nullable IChatBaseComponent var0) {
      this.h = var0 == null ? CommonComponents.a : var0;
      this.c.b(this);
   }

   public IChatBaseComponent f() {
      return this.h;
   }

   @Override
   public Collection<String> g() {
      return this.e;
   }

   @Override
   public IChatMutableComponent d(IChatBaseComponent var0) {
      IChatMutableComponent var1 = IChatBaseComponent.h().b(this.g).b(var0).b(this.h);
      EnumChatFormat var2 = this.n();
      if (var2 != EnumChatFormat.v) {
         var1.a(var2);
      }

      return var1;
   }

   public static IChatMutableComponent a(@Nullable ScoreboardTeamBase var0, IChatBaseComponent var1) {
      return var0 == null ? var1.e() : var0.d(var1);
   }

   @Override
   public boolean h() {
      return this.i;
   }

   public void a(boolean var0) {
      this.i = var0;
      this.c.b(this);
   }

   @Override
   public boolean i() {
      return this.j;
   }

   public void b(boolean var0) {
      this.j = var0;
      this.c.b(this);
   }

   @Override
   public ScoreboardTeamBase.EnumNameTagVisibility j() {
      return this.k;
   }

   @Override
   public ScoreboardTeamBase.EnumNameTagVisibility k() {
      return this.l;
   }

   public void a(ScoreboardTeamBase.EnumNameTagVisibility var0) {
      this.k = var0;
      this.c.b(this);
   }

   public void b(ScoreboardTeamBase.EnumNameTagVisibility var0) {
      this.l = var0;
      this.c.b(this);
   }

   @Override
   public ScoreboardTeamBase.EnumTeamPush l() {
      return this.n;
   }

   public void a(ScoreboardTeamBase.EnumTeamPush var0) {
      this.n = var0;
      this.c.b(this);
   }

   public int m() {
      int var0 = 0;
      if (this.h()) {
         var0 |= 1;
      }

      if (this.i()) {
         var0 |= 2;
      }

      return var0;
   }

   public void a(int var0) {
      this.a((var0 & 1) > 0);
      this.b((var0 & 2) > 0);
   }

   public void a(EnumChatFormat var0) {
      this.m = var0;
      this.c.b(this);
   }

   @Override
   public EnumChatFormat n() {
      return this.m;
   }
}
