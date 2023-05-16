package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.TaskChainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.command.VanillaCommandWrapper;
import org.spigotmc.SpigotConfig;

public class CommandListenerWrapper implements ICompletionProvider {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("permissions.requires.player"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("permissions.requires.entity"));
   public final ICommandListener c;
   private final Vec3D d;
   private final WorldServer e;
   private final int f;
   private final String g;
   private final IChatBaseComponent h;
   private final MinecraftServer i;
   private final boolean j;
   @Nullable
   private final Entity k;
   @Nullable
   private final ResultConsumer<CommandListenerWrapper> l;
   private final ArgumentAnchor.Anchor m;
   private final Vec2F n;
   private final CommandSigningContext o;
   private final TaskChainer p;
   public volatile CommandNode currentCommand;

   public CommandListenerWrapper(
      ICommandListener icommandlistener,
      Vec3D vec3d,
      Vec2F vec2f,
      WorldServer worldserver,
      int i,
      String s,
      IChatBaseComponent ichatbasecomponent,
      MinecraftServer minecraftserver,
      @Nullable Entity entity
   ) {
      this(icommandlistener, vec3d, vec2f, worldserver, i, s, ichatbasecomponent, minecraftserver, entity, false, (commandcontext, flag, j) -> {
      }, ArgumentAnchor.Anchor.a, CommandSigningContext.a, TaskChainer.immediate(minecraftserver));
   }

   protected CommandListenerWrapper(
      ICommandListener icommandlistener,
      Vec3D vec3d,
      Vec2F vec2f,
      WorldServer worldserver,
      int i,
      String s,
      IChatBaseComponent ichatbasecomponent,
      MinecraftServer minecraftserver,
      @Nullable Entity entity,
      boolean flag,
      @Nullable ResultConsumer<CommandListenerWrapper> resultconsumer,
      ArgumentAnchor.Anchor argumentanchor_anchor,
      CommandSigningContext commandsigningcontext,
      TaskChainer taskchainer
   ) {
      this.c = icommandlistener;
      this.d = vec3d;
      this.e = worldserver;
      this.j = flag;
      this.k = entity;
      this.f = i;
      this.g = s;
      this.h = ichatbasecomponent;
      this.i = minecraftserver;
      this.l = resultconsumer;
      this.m = argumentanchor_anchor;
      this.n = vec2f;
      this.o = commandsigningcontext;
      this.p = taskchainer;
   }

   public CommandListenerWrapper a(ICommandListener icommandlistener) {
      return this.c == icommandlistener
         ? this
         : new CommandListenerWrapper(icommandlistener, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m, this.o, this.p);
   }

   public CommandListenerWrapper a(Entity entity) {
      return this.k == entity
         ? this
         : new CommandListenerWrapper(
            this.c, this.d, this.n, this.e, this.f, entity.Z().getString(), entity.G_(), this.i, entity, this.j, this.l, this.m, this.o, this.p
         );
   }

   public CommandListenerWrapper a(Vec3D vec3d) {
      return this.d.equals(vec3d)
         ? this
         : new CommandListenerWrapper(this.c, vec3d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m, this.o, this.p);
   }

   public CommandListenerWrapper a(Vec2F vec2f) {
      return this.n.c(vec2f)
         ? this
         : new CommandListenerWrapper(this.c, this.d, vec2f, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m, this.o, this.p);
   }

   public CommandListenerWrapper a(ResultConsumer<CommandListenerWrapper> resultconsumer) {
      return Objects.equals(this.l, resultconsumer)
         ? this
         : new CommandListenerWrapper(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, resultconsumer, this.m, this.o, this.p);
   }

   public CommandListenerWrapper a(
      ResultConsumer<CommandListenerWrapper> resultconsumer, BinaryOperator<ResultConsumer<CommandListenerWrapper>> binaryoperator
   ) {
      ResultConsumer<CommandListenerWrapper> resultconsumer1 = (ResultConsumer)binaryoperator.apply(this.l, resultconsumer);
      return this.a(resultconsumer1);
   }

   public CommandListenerWrapper a() {
      return !this.j && !this.c.e_()
         ? new CommandListenerWrapper(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, true, this.l, this.m, this.o, this.p)
         : this;
   }

   public CommandListenerWrapper a(int i) {
      return i == this.f
         ? this
         : new CommandListenerWrapper(this.c, this.d, this.n, this.e, i, this.g, this.h, this.i, this.k, this.j, this.l, this.m, this.o, this.p);
   }

   public CommandListenerWrapper b(int i) {
      return i <= this.f
         ? this
         : new CommandListenerWrapper(this.c, this.d, this.n, this.e, i, this.g, this.h, this.i, this.k, this.j, this.l, this.m, this.o, this.p);
   }

   public CommandListenerWrapper a(ArgumentAnchor.Anchor argumentanchor_anchor) {
      return argumentanchor_anchor == this.m
         ? this
         : new CommandListenerWrapper(
            this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, argumentanchor_anchor, this.o, this.p
         );
   }

   public CommandListenerWrapper a(WorldServer worldserver) {
      if (worldserver == this.e) {
         return this;
      } else {
         double d0 = DimensionManager.a(this.e.q_(), worldserver.q_());
         Vec3D vec3d = new Vec3D(this.d.c * d0, this.d.d, this.d.e * d0);
         return new CommandListenerWrapper(this.c, vec3d, this.n, worldserver, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m, this.o, this.p);
      }
   }

   public CommandListenerWrapper a(Entity entity, ArgumentAnchor.Anchor argumentanchor_anchor) {
      return this.b(argumentanchor_anchor.a(entity));
   }

   public CommandListenerWrapper b(Vec3D vec3d) {
      Vec3D vec3d1 = this.m.a(this);
      double d0 = vec3d.c - vec3d1.c;
      double d1 = vec3d.d - vec3d1.d;
      double d2 = vec3d.e - vec3d1.e;
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = MathHelper.g((float)(-(MathHelper.d(d1, d3) * 180.0F / (float)Math.PI)));
      float f1 = MathHelper.g((float)(MathHelper.d(d2, d0) * 180.0F / (float)Math.PI) - 90.0F);
      return this.a(new Vec2F(f, f1));
   }

   public CommandListenerWrapper a(CommandSigningContext commandsigningcontext) {
      return commandsigningcontext == this.o
         ? this
         : new CommandListenerWrapper(
            this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m, commandsigningcontext, this.p
         );
   }

   public CommandListenerWrapper a(TaskChainer taskchainer) {
      return taskchainer == this.p
         ? this
         : new CommandListenerWrapper(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m, this.o, taskchainer);
   }

   public IChatBaseComponent b() {
      return this.h;
   }

   public String c() {
      return this.g;
   }

   @Override
   public boolean c(int i) {
      CommandNode currentCommand = this.currentCommand;
      if (currentCommand != null) {
         return this.hasPermission(i, VanillaCommandWrapper.getPermission(currentCommand));
      } else {
         return this.f >= i;
      }
   }

   public boolean hasPermission(int i, String bukkitPermission) {
      return (this.e() == null || !this.e().getCraftServer().ignoreVanillaPermissions) && this.f >= i
         || this.getBukkitSender().hasPermission(bukkitPermission);
   }

   public Vec3D d() {
      return this.d;
   }

   public WorldServer e() {
      return this.e;
   }

   @Nullable
   public Entity f() {
      return this.k;
   }

   public Entity g() throws CommandSyntaxException {
      if (this.k == null) {
         throw b.create();
      } else {
         return this.k;
      }
   }

   public EntityPlayer h() throws CommandSyntaxException {
      Entity entity = this.k;
      if (entity instanceof EntityPlayer) {
         return (EntityPlayer)entity;
      } else {
         throw a.create();
      }
   }

   @Nullable
   public EntityPlayer i() {
      Entity entity = this.k;
      EntityPlayer entityplayer;
      if (entity instanceof EntityPlayer entityplayer1) {
         entityplayer = entityplayer1;
      } else {
         entityplayer = null;
      }

      return entityplayer;
   }

   public boolean j() {
      return this.k instanceof EntityPlayer;
   }

   public Vec2F k() {
      return this.n;
   }

   public MinecraftServer l() {
      return this.i;
   }

   public ArgumentAnchor.Anchor m() {
      return this.m;
   }

   public CommandSigningContext n() {
      return this.o;
   }

   public TaskChainer o() {
      return this.p;
   }

   public boolean a(EntityPlayer entityplayer) {
      EntityPlayer entityplayer1 = this.i();
      return entityplayer == entityplayer1 ? false : entityplayer1 != null && entityplayer1.U() || entityplayer.U();
   }

   public void a(OutgoingChatMessage outgoingchatmessage, boolean flag, ChatMessageType.a chatmessagetype_a) {
      if (!this.j) {
         EntityPlayer entityplayer = this.i();
         if (entityplayer != null) {
            entityplayer.a(outgoingchatmessage, flag, chatmessagetype_a);
         } else {
            this.c.a(chatmessagetype_a.a(outgoingchatmessage.a()));
         }
      }
   }

   public void a(IChatBaseComponent ichatbasecomponent) {
      if (!this.j) {
         EntityPlayer entityplayer = this.i();
         if (entityplayer != null) {
            entityplayer.a(ichatbasecomponent);
         } else {
            this.c.a(ichatbasecomponent);
         }
      }
   }

   public void a(IChatBaseComponent ichatbasecomponent, boolean flag) {
      if (this.c.d_() && !this.j) {
         this.c.a(ichatbasecomponent);
      }

      if (flag && this.c.M_() && !this.j) {
         this.c(ichatbasecomponent);
      }
   }

   private void c(IChatBaseComponent ichatbasecomponent) {
      IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.a("chat.type.admin", this.b(), ichatbasecomponent)
         .a(EnumChatFormat.h, EnumChatFormat.u);
      if (this.i.aK().b(GameRules.o)) {
         for(EntityPlayer entityplayer : this.i.ac().t()) {
            if (entityplayer != this.c && entityplayer.getBukkitEntity().hasPermission("minecraft.admin.command_feedback")) {
               entityplayer.a(ichatmutablecomponent);
            }
         }
      }

      if (this.c != this.i && this.i.aK().b(GameRules.l) && !SpigotConfig.silentCommandBlocks) {
         this.i.a(ichatmutablecomponent);
      }
   }

   public void b(IChatBaseComponent ichatbasecomponent) {
      if (this.c.j_() && !this.j) {
         this.c.a(IChatBaseComponent.h().b(ichatbasecomponent).a(EnumChatFormat.m));
      }
   }

   public void a(CommandContext<CommandListenerWrapper> commandcontext, boolean flag, int i) {
      if (this.l != null) {
         this.l.onCommandComplete(commandcontext, flag, i);
      }
   }

   @Override
   public Collection<String> p() {
      return Lists.newArrayList(this.i.J());
   }

   @Override
   public Collection<String> q() {
      return this.i.aF().f();
   }

   @Override
   public Stream<MinecraftKey> r() {
      return BuiltInRegistries.c.s().map(SoundEffect::a);
   }

   @Override
   public Stream<MinecraftKey> s() {
      return this.i.aE().d();
   }

   @Override
   public CompletableFuture<Suggestions> a(CommandContext<?> commandcontext) {
      return Suggestions.empty();
   }

   @Override
   public CompletableFuture<Suggestions> a(
      ResourceKey<? extends IRegistry<?>> resourcekey,
      ICompletionProvider.a icompletionprovider_a,
      SuggestionsBuilder suggestionsbuilder,
      CommandContext<?> commandcontext
   ) {
      return this.u().c(resourcekey).map(iregistry -> {
         this.a(iregistry, icompletionprovider_a, suggestionsbuilder);
         return suggestionsbuilder.buildFuture();
      }).orElseGet(Suggestions::empty);
   }

   @Override
   public Set<ResourceKey<World>> t() {
      return this.i.E();
   }

   @Override
   public IRegistryCustom u() {
      return this.i.aX();
   }

   @Override
   public FeatureFlagSet v() {
      return this.e.G();
   }

   public CommandSender getBukkitSender() {
      return this.c.getBukkitSender(this);
   }
}
