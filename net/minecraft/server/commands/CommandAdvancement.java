package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandException;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;

public class CommandAdvancement {
   private static final SuggestionProvider<CommandListenerWrapper> a = (var0, var1) -> {
      Collection<Advancement> var2 = ((CommandListenerWrapper)var0.getSource()).l().az().a();
      return ICompletionProvider.a(var2.stream().map(Advancement::i), var1);
   };

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "advancement"
                  )
                  .requires(var0x -> var0x.c(2)))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("grant")
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                          "targets", ArgumentEntity.d()
                                       )
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("only")
                                             .then(
                                                ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                         "advancement", ArgumentMinecraftKeyRegistered.a()
                                                      )
                                                      .suggests(a)
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentEntity.f(var0x, "targets"),
                                                               CommandAdvancement.Action.a,
                                                               a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.a)
                                                            )
                                                      ))
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("criterion", StringArgumentType.greedyString())
                                                         .suggests(
                                                            (var0x, var1) -> ICompletionProvider.b(
                                                                  ArgumentMinecraftKeyRegistered.a(var0x, "advancement").g().keySet(), var1
                                                               )
                                                         )
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(),
                                                                  ArgumentEntity.f(var0x, "targets"),
                                                                  CommandAdvancement.Action.a,
                                                                  ArgumentMinecraftKeyRegistered.a(var0x, "advancement"),
                                                                  StringArgumentType.getString(var0x, "criterion")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("from")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a())
                                                .suggests(a)
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         ArgumentEntity.f(var0x, "targets"),
                                                         CommandAdvancement.Action.a,
                                                         a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.c)
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("until")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a())
                                             .suggests(a)
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentEntity.f(var0x, "targets"),
                                                      CommandAdvancement.Action.a,
                                                      a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.d)
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("through")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a())
                                          .suggests(a)
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.f(var0x, "targets"),
                                                   CommandAdvancement.Action.a,
                                                   a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.b)
                                                )
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("everything")
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentEntity.f(var0x, "targets"),
                                          CommandAdvancement.Action.a,
                                          ((CommandListenerWrapper)var0x.getSource()).l().az().a()
                                       )
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("revoke")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                       "targets", ArgumentEntity.d()
                                    )
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("only")
                                          .then(
                                             ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                      "advancement", ArgumentMinecraftKeyRegistered.a()
                                                   )
                                                   .suggests(a)
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentEntity.f(var0x, "targets"),
                                                            CommandAdvancement.Action.b,
                                                            a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.a)
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("criterion", StringArgumentType.greedyString())
                                                      .suggests(
                                                         (var0x, var1) -> ICompletionProvider.b(
                                                               ArgumentMinecraftKeyRegistered.a(var0x, "advancement").g().keySet(), var1
                                                            )
                                                      )
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentEntity.f(var0x, "targets"),
                                                               CommandAdvancement.Action.b,
                                                               ArgumentMinecraftKeyRegistered.a(var0x, "advancement"),
                                                               StringArgumentType.getString(var0x, "criterion")
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("from")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a())
                                             .suggests(a)
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentEntity.f(var0x, "targets"),
                                                      CommandAdvancement.Action.b,
                                                      a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.c)
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("until")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a())
                                          .suggests(a)
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.f(var0x, "targets"),
                                                   CommandAdvancement.Action.b,
                                                   a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.d)
                                                )
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("through")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a())
                                       .suggests(a)
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentEntity.f(var0x, "targets"),
                                                CommandAdvancement.Action.b,
                                                a(ArgumentMinecraftKeyRegistered.a(var0x, "advancement"), CommandAdvancement.Filter.b)
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("everything")
                              .executes(
                                 var0x -> a(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       ArgumentEntity.f(var0x, "targets"),
                                       CommandAdvancement.Action.b,
                                       ((CommandListenerWrapper)var0x.getSource()).l().az().a()
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, CommandAdvancement.Action var2, Collection<Advancement> var3) {
      int var4 = 0;

      for(EntityPlayer var6 : var1) {
         var4 += var2.a(var6, var3);
      }

      if (var4 == 0) {
         if (var3.size() == 1) {
            if (var1.size() == 1) {
               throw new CommandException(IChatBaseComponent.a(var2.a() + ".one.to.one.failure", var3.iterator().next().k(), var1.iterator().next().G_()));
            } else {
               throw new CommandException(IChatBaseComponent.a(var2.a() + ".one.to.many.failure", var3.iterator().next().k(), var1.size()));
            }
         } else if (var1.size() == 1) {
            throw new CommandException(IChatBaseComponent.a(var2.a() + ".many.to.one.failure", var3.size(), var1.iterator().next().G_()));
         } else {
            throw new CommandException(IChatBaseComponent.a(var2.a() + ".many.to.many.failure", var3.size(), var1.size()));
         }
      } else {
         if (var3.size() == 1) {
            if (var1.size() == 1) {
               var0.a(IChatBaseComponent.a(var2.a() + ".one.to.one.success", var3.iterator().next().k(), var1.iterator().next().G_()), true);
            } else {
               var0.a(IChatBaseComponent.a(var2.a() + ".one.to.many.success", var3.iterator().next().k(), var1.size()), true);
            }
         } else if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a(var2.a() + ".many.to.one.success", var3.size(), var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a(var2.a() + ".many.to.many.success", var3.size(), var1.size()), true);
         }

         return var4;
      }
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, CommandAdvancement.Action var2, Advancement var3, String var4) {
      int var5 = 0;
      if (!var3.g().containsKey(var4)) {
         throw new CommandException(IChatBaseComponent.a("commands.advancement.criterionNotFound", var3.k(), var4));
      } else {
         for(EntityPlayer var7 : var1) {
            if (var2.a(var7, var3, var4)) {
               ++var5;
            }
         }

         if (var5 == 0) {
            if (var1.size() == 1) {
               throw new CommandException(IChatBaseComponent.a(var2.a() + ".criterion.to.one.failure", var4, var3.k(), var1.iterator().next().G_()));
            } else {
               throw new CommandException(IChatBaseComponent.a(var2.a() + ".criterion.to.many.failure", var4, var3.k(), var1.size()));
            }
         } else {
            if (var1.size() == 1) {
               var0.a(IChatBaseComponent.a(var2.a() + ".criterion.to.one.success", var4, var3.k(), var1.iterator().next().G_()), true);
            } else {
               var0.a(IChatBaseComponent.a(var2.a() + ".criterion.to.many.success", var4, var3.k(), var1.size()), true);
            }

            return var5;
         }
      }
   }

   private static List<Advancement> a(Advancement var0, CommandAdvancement.Filter var1) {
      List<Advancement> var2 = Lists.newArrayList();
      if (var1.f) {
         for(Advancement var3 = var0.b(); var3 != null; var3 = var3.b()) {
            var2.add(var3);
         }
      }

      var2.add(var0);
      if (var1.g) {
         a(var0, var2);
      }

      return var2;
   }

   private static void a(Advancement var0, List<Advancement> var1) {
      for(Advancement var3 : var0.f()) {
         var1.add(var3);
         a(var3, var1);
      }
   }

   static enum Action {
      a("grant") {
         @Override
         protected boolean a(EntityPlayer var0, Advancement var1) {
            AdvancementProgress var2 = var0.M().b(var1);
            if (var2.a()) {
               return false;
            } else {
               for(String var4 : var2.e()) {
                  var0.M().a(var1, var4);
               }

               return true;
            }
         }

         @Override
         protected boolean a(EntityPlayer var0, Advancement var1, String var2) {
            return var0.M().a(var1, var2);
         }
      },
      b("revoke") {
         @Override
         protected boolean a(EntityPlayer var0, Advancement var1) {
            AdvancementProgress var2 = var0.M().b(var1);
            if (!var2.b()) {
               return false;
            } else {
               for(String var4 : var2.f()) {
                  var0.M().b(var1, var4);
               }

               return true;
            }
         }

         @Override
         protected boolean a(EntityPlayer var0, Advancement var1, String var2) {
            return var0.M().b(var1, var2);
         }
      };

      private final String c;

      Action(String var2) {
         this.c = "commands.advancement." + var2;
      }

      public int a(EntityPlayer var0, Iterable<Advancement> var1) {
         int var2 = 0;

         for(Advancement var4 : var1) {
            if (this.a(var0, var4)) {
               ++var2;
            }
         }

         return var2;
      }

      protected abstract boolean a(EntityPlayer var1, Advancement var2);

      protected abstract boolean a(EntityPlayer var1, Advancement var2, String var3);

      protected String a() {
         return this.c;
      }
   }

   static enum Filter {
      a(false, false),
      b(true, true),
      c(false, true),
      d(true, false),
      e(true, true);

      final boolean f;
      final boolean g;

      private Filter(boolean var2, boolean var3) {
         this.f = var2;
         this.g = var3;
      }
   }
}
