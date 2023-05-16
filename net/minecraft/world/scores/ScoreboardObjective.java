package net.minecraft.world.scores;

import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class ScoreboardObjective {
   private final Scoreboard a;
   private final String b;
   private final IScoreboardCriteria c;
   public IChatBaseComponent d;
   private IChatBaseComponent e;
   private IScoreboardCriteria.EnumScoreboardHealthDisplay f;

   public ScoreboardObjective(
      Scoreboard var0, String var1, IScoreboardCriteria var2, IChatBaseComponent var3, IScoreboardCriteria.EnumScoreboardHealthDisplay var4
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = this.g();
      this.f = var4;
   }

   public Scoreboard a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   public IScoreboardCriteria c() {
      return this.c;
   }

   public IChatBaseComponent d() {
      return this.d;
   }

   private IChatBaseComponent g() {
      return ChatComponentUtils.a(
         (IChatBaseComponent)this.d.e().a(var0 -> var0.a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.b(this.b))))
      );
   }

   public IChatBaseComponent e() {
      return this.e;
   }

   public void a(IChatBaseComponent var0) {
      this.d = var0;
      this.e = this.g();
      this.a.b(this);
   }

   public IScoreboardCriteria.EnumScoreboardHealthDisplay f() {
      return this.f;
   }

   public void a(IScoreboardCriteria.EnumScoreboardHealthDisplay var0) {
      this.f = var0;
      this.a.b(this);
   }
}
