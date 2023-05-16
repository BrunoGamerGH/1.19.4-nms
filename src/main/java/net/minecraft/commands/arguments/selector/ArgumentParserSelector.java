package net.minecraft.commands.arguments.selector;

import com.google.common.primitives.Doubles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.CriterionConditionRange;
import net.minecraft.advancements.critereon.CriterionConditionValue;
import net.minecraft.commands.arguments.selector.options.PlayerSelector;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;

public class ArgumentParserSelector {
   public static final char a = '@';
   private static final char o = '[';
   private static final char p = ']';
   public static final char b = '=';
   private static final char q = ',';
   public static final char c = '!';
   public static final char d = '#';
   private static final char r = 'p';
   private static final char s = 'a';
   private static final char t = 'r';
   private static final char u = 's';
   private static final char v = 'e';
   public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.invalid"));
   public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(
      object -> IChatBaseComponent.a("argument.entity.selector.unknown", object)
   );
   public static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.selector.not_allowed"));
   public static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.selector.missing"));
   public static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.options.unterminated"));
   public static final DynamicCommandExceptionType j = new DynamicCommandExceptionType(
      object -> IChatBaseComponent.a("argument.entity.options.valueless", object)
   );
   public static final BiConsumer<Vec3D, List<? extends Entity>> k = (vec3d, list) -> list.sort(
         (entity, entity1) -> Doubles.compare(entity.e(vec3d), entity1.e(vec3d))
      );
   public static final BiConsumer<Vec3D, List<? extends Entity>> l = (vec3d, list) -> list.sort(
         (entity, entity1) -> Doubles.compare(entity1.e(vec3d), entity.e(vec3d))
      );
   public static final BiConsumer<Vec3D, List<? extends Entity>> m = (vec3d, list) -> Collections.shuffle(list);
   public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> n = (suggestionsbuilder, consumer) -> suggestionsbuilder.buildFuture(
         
      );
   private final StringReader w;
   private final boolean x;
   private int y;
   private boolean z;
   private boolean A;
   private CriterionConditionValue.DoubleRange B = CriterionConditionValue.DoubleRange.e;
   private CriterionConditionValue.IntegerRange C = CriterionConditionValue.IntegerRange.e;
   @Nullable
   private Double D;
   @Nullable
   private Double E;
   @Nullable
   private Double F;
   @Nullable
   private Double G;
   @Nullable
   private Double H;
   @Nullable
   private Double I;
   private CriterionConditionRange J = CriterionConditionRange.a;
   private CriterionConditionRange K = CriterionConditionRange.a;
   private Predicate<Entity> L = entity -> true;
   private BiConsumer<Vec3D, List<? extends Entity>> M = EntitySelector.b;
   private boolean N;
   @Nullable
   private String O;
   private int P;
   @Nullable
   private UUID Q;
   private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> R = n;
   private boolean S;
   private boolean T;
   private boolean U;
   private boolean V;
   private boolean W;
   private boolean X;
   private boolean Y;
   private boolean Z;
   @Nullable
   private EntityTypes<?> aa;
   private boolean ab;
   private boolean ac;
   private boolean ad;
   private boolean ae;

   public ArgumentParserSelector(StringReader stringreader) {
      this(stringreader, true);
   }

   public ArgumentParserSelector(StringReader stringreader, boolean flag) {
      this.w = stringreader;
      this.x = flag;
   }

   public EntitySelector a() {
      AxisAlignedBB axisalignedbb;
      if (this.G == null && this.H == null && this.I == null) {
         if (this.B.b() != null) {
            double d0 = this.B.b();
            axisalignedbb = new AxisAlignedBB(-d0, -d0, -d0, d0 + 1.0, d0 + 1.0, d0 + 1.0);
         } else {
            axisalignedbb = null;
         }
      } else {
         axisalignedbb = this.a(this.G == null ? 0.0 : this.G, this.H == null ? 0.0 : this.H, this.I == null ? 0.0 : this.I);
      }

      Function<Vec3D, Vec3D> function;
      if (this.D == null && this.E == null && this.F == null) {
         function = vec3d -> vec3d;
      } else {
         function = vec3d -> new Vec3D(this.D == null ? vec3d.c : this.D, this.E == null ? vec3d.d : this.E, this.F == null ? vec3d.e : this.F);
      }

      return new EntitySelector(this.y, this.z, this.A, this.L, this.B, function, axisalignedbb, this.M, this.N, this.O, this.Q, this.aa, this.ae);
   }

   private AxisAlignedBB a(double d0, double d1, double d2) {
      boolean flag = d0 < 0.0;
      boolean flag1 = d1 < 0.0;
      boolean flag2 = d2 < 0.0;
      double d3 = flag ? d0 : 0.0;
      double d4 = flag1 ? d1 : 0.0;
      double d5 = flag2 ? d2 : 0.0;
      double d6 = (flag ? 0.0 : d0) + 1.0;
      double d7 = (flag1 ? 0.0 : d1) + 1.0;
      double d8 = (flag2 ? 0.0 : d2) + 1.0;
      return new AxisAlignedBB(d3, d4, d5, d6, d7, d8);
   }

   private void I() {
      if (this.J != CriterionConditionRange.a) {
         this.L = this.L.and(this.a(this.J, Entity::dy));
      }

      if (this.K != CriterionConditionRange.a) {
         this.L = this.L.and(this.a(this.K, Entity::dw));
      }

      if (!this.C.c()) {
         this.L = this.L.and(entity -> !(entity instanceof EntityPlayer) ? false : this.C.d(((EntityPlayer)entity).cc));
      }
   }

   private Predicate<Entity> a(CriterionConditionRange criterionconditionrange, ToDoubleFunction<Entity> todoublefunction) {
      double d0 = (double)MathHelper.g(criterionconditionrange.a() == null ? 0.0F : criterionconditionrange.a());
      double d1 = (double)MathHelper.g(criterionconditionrange.b() == null ? 359.0F : criterionconditionrange.b());
      return entity -> {
         double d2 = MathHelper.d(todoublefunction.applyAsDouble(entity));
         return d0 > d1 ? d2 >= d0 || d2 <= d1 : d2 >= d0 && d2 <= d1;
      };
   }

   protected void parseSelector(boolean overridePermissions) throws CommandSyntaxException {
      this.ae = !overridePermissions;
      this.R = this::d;
      if (!this.w.canRead()) {
         throw h.createWithContext(this.w);
      } else {
         int i = this.w.getCursor();
         char c0 = this.w.read();
         if (c0 == 'p') {
            this.y = 1;
            this.z = false;
            this.M = k;
            this.a(EntityTypes.bt);
         } else if (c0 == 'a') {
            this.y = Integer.MAX_VALUE;
            this.z = false;
            this.M = EntitySelector.b;
            this.a(EntityTypes.bt);
         } else if (c0 == 'r') {
            this.y = 1;
            this.z = false;
            this.M = m;
            this.a(EntityTypes.bt);
         } else if (c0 == 's') {
            this.y = 1;
            this.z = true;
            this.N = true;
         } else {
            if (c0 != 'e') {
               this.w.setCursor(i);
               throw f.createWithContext(this.w, "@" + String.valueOf(c0));
            }

            this.y = Integer.MAX_VALUE;
            this.z = true;
            this.M = EntitySelector.b;
            this.L = Entity::bq;
         }

         this.R = this::e;
         if (this.w.canRead() && this.w.peek() == '[') {
            this.w.skip();
            this.R = this::f;
            this.d();
         }
      }
   }

   protected void c() throws CommandSyntaxException {
      if (this.w.canRead()) {
         this.R = this::c;
      }

      int i = this.w.getCursor();
      String s = this.w.readString();

      try {
         this.Q = UUID.fromString(s);
         this.z = true;
      } catch (IllegalArgumentException var5) {
         if (s.isEmpty() || s.length() > 16) {
            this.w.setCursor(i);
            throw e.createWithContext(this.w);
         }

         this.z = false;
         this.O = s;
      }

      this.y = 1;
   }

   protected void d() throws CommandSyntaxException {
      this.R = this::g;
      this.w.skipWhitespace();

      while(this.w.canRead() && this.w.peek() != ']') {
         this.w.skipWhitespace();
         int i = this.w.getCursor();
         String s = this.w.readString();
         PlayerSelector.a playerselector_a = PlayerSelector.a(this, s, i);
         this.w.skipWhitespace();
         if (!this.w.canRead() || this.w.peek() != '=') {
            this.w.setCursor(i);
            throw j.createWithContext(this.w, s);
         }

         this.w.skip();
         this.w.skipWhitespace();
         this.R = n;
         playerselector_a.handle(this);
         this.w.skipWhitespace();
         this.R = this::h;
         if (this.w.canRead()) {
            if (this.w.peek() != ',') {
               if (this.w.peek() != ']') {
                  throw ArgumentParserSelector.i.createWithContext(this.w);
               }
               break;
            }

            this.w.skip();
            this.R = this::g;
         }
      }

      if (this.w.canRead()) {
         this.w.skip();
         this.R = n;
      } else {
         throw ArgumentParserSelector.i.createWithContext(this.w);
      }
   }

   public boolean e() {
      this.w.skipWhitespace();
      if (this.w.canRead() && this.w.peek() == '!') {
         this.w.skip();
         this.w.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public boolean f() {
      this.w.skipWhitespace();
      if (this.w.canRead() && this.w.peek() == '#') {
         this.w.skip();
         this.w.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public StringReader g() {
      return this.w;
   }

   public void a(Predicate<Entity> predicate) {
      this.L = this.L.and(predicate);
   }

   public void h() {
      this.A = true;
   }

   public CriterionConditionValue.DoubleRange i() {
      return this.B;
   }

   public void a(CriterionConditionValue.DoubleRange criterionconditionvalue_doublerange) {
      this.B = criterionconditionvalue_doublerange;
   }

   public CriterionConditionValue.IntegerRange j() {
      return this.C;
   }

   public void a(CriterionConditionValue.IntegerRange criterionconditionvalue_integerrange) {
      this.C = criterionconditionvalue_integerrange;
   }

   public CriterionConditionRange k() {
      return this.J;
   }

   public void a(CriterionConditionRange criterionconditionrange) {
      this.J = criterionconditionrange;
   }

   public CriterionConditionRange l() {
      return this.K;
   }

   public void b(CriterionConditionRange criterionconditionrange) {
      this.K = criterionconditionrange;
   }

   @Nullable
   public Double m() {
      return this.D;
   }

   @Nullable
   public Double n() {
      return this.E;
   }

   @Nullable
   public Double o() {
      return this.F;
   }

   public void a(double d0) {
      this.D = d0;
   }

   public void b(double d0) {
      this.E = d0;
   }

   public void c(double d0) {
      this.F = d0;
   }

   public void d(double d0) {
      this.G = d0;
   }

   public void e(double d0) {
      this.H = d0;
   }

   public void f(double d0) {
      this.I = d0;
   }

   @Nullable
   public Double p() {
      return this.G;
   }

   @Nullable
   public Double q() {
      return this.H;
   }

   @Nullable
   public Double r() {
      return this.I;
   }

   public void a(int i) {
      this.y = i;
   }

   public void a(boolean flag) {
      this.z = flag;
   }

   public BiConsumer<Vec3D, List<? extends Entity>> s() {
      return this.M;
   }

   public void a(BiConsumer<Vec3D, List<? extends Entity>> biconsumer) {
      this.M = biconsumer;
   }

   public EntitySelector t() throws CommandSyntaxException {
      return this.parse(false);
   }

   public EntitySelector parse(boolean overridePermissions) throws CommandSyntaxException {
      this.P = this.w.getCursor();
      this.R = this::b;
      if (this.w.canRead() && this.w.peek() == '@') {
         if (!this.x) {
            throw g.createWithContext(this.w);
         }

         this.w.skip();
         this.parseSelector(overridePermissions);
      } else {
         this.c();
      }

      this.I();
      return this.a();
   }

   private static void a(SuggestionsBuilder suggestionsbuilder) {
      suggestionsbuilder.suggest("@p", IChatBaseComponent.c("argument.entity.selector.nearestPlayer"));
      suggestionsbuilder.suggest("@a", IChatBaseComponent.c("argument.entity.selector.allPlayers"));
      suggestionsbuilder.suggest("@r", IChatBaseComponent.c("argument.entity.selector.randomPlayer"));
      suggestionsbuilder.suggest("@s", IChatBaseComponent.c("argument.entity.selector.self"));
      suggestionsbuilder.suggest("@e", IChatBaseComponent.c("argument.entity.selector.allEntities"));
   }

   private CompletableFuture<Suggestions> b(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      consumer.accept(suggestionsbuilder);
      if (this.x) {
         a(suggestionsbuilder);
      }

      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> c(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      SuggestionsBuilder suggestionsbuilder1 = suggestionsbuilder.createOffset(this.P);
      consumer.accept(suggestionsbuilder1);
      return suggestionsbuilder.add(suggestionsbuilder1).buildFuture();
   }

   private CompletableFuture<Suggestions> d(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      SuggestionsBuilder suggestionsbuilder1 = suggestionsbuilder.createOffset(suggestionsbuilder.getStart() - 1);
      a(suggestionsbuilder1);
      suggestionsbuilder.add(suggestionsbuilder1);
      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> e(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      suggestionsbuilder.suggest(String.valueOf('['));
      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> f(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      suggestionsbuilder.suggest(String.valueOf(']'));
      PlayerSelector.a(this, suggestionsbuilder);
      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> g(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      PlayerSelector.a(this, suggestionsbuilder);
      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> h(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      suggestionsbuilder.suggest(String.valueOf(','));
      suggestionsbuilder.suggest(String.valueOf(']'));
      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> i(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      suggestionsbuilder.suggest(String.valueOf('='));
      return suggestionsbuilder.buildFuture();
   }

   public boolean u() {
      return this.N;
   }

   public void a(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> bifunction) {
      this.R = bifunction;
   }

   public CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsbuilder, Consumer<SuggestionsBuilder> consumer) {
      return this.R.apply(suggestionsbuilder.createOffset(this.w.getCursor()), consumer);
   }

   public boolean v() {
      return this.S;
   }

   public void b(boolean flag) {
      this.S = flag;
   }

   public boolean w() {
      return this.T;
   }

   public void c(boolean flag) {
      this.T = flag;
   }

   public boolean x() {
      return this.U;
   }

   public void d(boolean flag) {
      this.U = flag;
   }

   public boolean y() {
      return this.V;
   }

   public void e(boolean flag) {
      this.V = flag;
   }

   public boolean z() {
      return this.W;
   }

   public void f(boolean flag) {
      this.W = flag;
   }

   public boolean A() {
      return this.X;
   }

   public void g(boolean flag) {
      this.X = flag;
   }

   public boolean B() {
      return this.Y;
   }

   public void h(boolean flag) {
      this.Y = flag;
   }

   public boolean C() {
      return this.Z;
   }

   public void i(boolean flag) {
      this.Z = flag;
   }

   public void a(EntityTypes<?> entitytypes) {
      this.aa = entitytypes;
   }

   public void D() {
      this.ab = true;
   }

   public boolean E() {
      return this.aa != null;
   }

   public boolean F() {
      return this.ab;
   }

   public boolean G() {
      return this.ac;
   }

   public void j(boolean flag) {
      this.ac = flag;
   }

   public boolean H() {
      return this.ad;
   }

   public void k(boolean flag) {
      this.ad = flag;
   }
}
