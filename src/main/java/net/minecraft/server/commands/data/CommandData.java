package net.minecraft.server.commands.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentNBTBase;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.commands.arguments.ArgumentNBTTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTList;
import net.minecraft.nbt.NBTNumber;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;

public class CommandData {
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.data.merge.failed"));
   private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.data.get.invalid", var0));
   private static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.data.get.unknown", var0));
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.data.get.multiple"));
   private static final DynamicCommandExceptionType h = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.data.modify.expected_object", var0)
   );
   private static final DynamicCommandExceptionType i = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.data.modify.expected_value", var0)
   );
   public static final List<Function<String, CommandData.c>> a = ImmutableList.of(CommandDataAccessorEntity.a, CommandDataAccessorTile.a, CommandDataStorage.a);
   public static final List<CommandData.c> b = a.stream().map(var0 -> var0.apply("target")).collect(ImmutableList.toImmutableList());
   public static final List<CommandData.c> c = a.stream().map(var0 -> var0.apply("source")).collect(ImmutableList.toImmutableList());

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      LiteralArgumentBuilder<CommandListenerWrapper> var1 = (LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("data")
         .requires(var0x -> var0x.c(2));

      for(CommandData.c var3 : b) {
         ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)var1.then(
                     var3.a(
                        net.minecraft.commands.CommandDispatcher.a("merge"),
                        var1x -> var1x.then(
                              net.minecraft.commands.CommandDispatcher.a("nbt", ArgumentNBTTag.a())
                                 .executes(var1xx -> a((CommandListenerWrapper)var1xx.getSource(), var3.a(var1xx), ArgumentNBTTag.a(var1xx, "nbt")))
                           )
                     )
                  ))
                  .then(
                     var3.a(
                        net.minecraft.commands.CommandDispatcher.a("get"),
                        var1x -> var1x.executes(var1xx -> a((CommandListenerWrapper)var1xx.getSource(), var3.a(var1xx)))
                              .then(
                                 ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("path", ArgumentNBTKey.a())
                                       .executes(var1xx -> b((CommandListenerWrapper)var1xx.getSource(), var3.a(var1xx), ArgumentNBTKey.a(var1xx, "path"))))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             var1xx -> a(
                                                   (CommandListenerWrapper)var1xx.getSource(),
                                                   var3.a(var1xx),
                                                   ArgumentNBTKey.a(var1xx, "path"),
                                                   DoubleArgumentType.getDouble(var1xx, "scale")
                                                )
                                          )
                                    )
                              )
                     )
                  ))
               .then(
                  var3.a(
                     net.minecraft.commands.CommandDispatcher.a("remove"),
                     var1x -> var1x.then(
                           net.minecraft.commands.CommandDispatcher.a("path", ArgumentNBTKey.a())
                              .executes(var1xx -> a((CommandListenerWrapper)var1xx.getSource(), var3.a(var1xx), ArgumentNBTKey.a(var1xx, "path")))
                        )
                  )
               ))
            .then(
               a(
                  (var0x, var1x) -> var0x.then(
                           net.minecraft.commands.CommandDispatcher.a("insert")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("index", IntegerArgumentType.integer())
                                    .then(
                                       var1x.create((var0xx, var1xx, var2, var3x) -> var2.a(IntegerArgumentType.getInteger(var0xx, "index"), var1xx, var3x))
                                    )
                              )
                        )
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("prepend").then(var1x.create((var0xx, var1xx, var2, var3x) -> var2.a(0, var1xx, var3x)))
                        )
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("append").then(var1x.create((var0xx, var1xx, var2, var3x) -> var2.a(-1, var1xx, var3x)))
                        )
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("set")
                              .then(var1x.create((var0xx, var1xx, var2, var3x) -> var2.a(var1xx, (NBTBase)Iterables.getLast(var3x))))
                        )
                        .then(net.minecraft.commands.CommandDispatcher.a("merge").then(var1x.create((var0xx, var1xx, var2, var3x) -> {
                           NBTTagCompound var4 = new NBTTagCompound();
            
                           for(NBTBase var6 : var3x) {
                              if (ArgumentNBTKey.g.a(var6, 0)) {
                                 throw ArgumentNBTKey.b.create();
                              }
            
                              if (!(var6 instanceof NBTTagCompound)) {
                                 throw h.create(var6);
                              }
            
                              NBTTagCompound var7 = (NBTTagCompound)var6;
                              var4.a(var7);
                           }
            
                           Collection<NBTBase> var5 = var2.a(var1xx, NBTTagCompound::new);
                           int var6 = 0;
            
                           for(NBTBase var8 : var5) {
                              if (!(var8 instanceof NBTTagCompound)) {
                                 throw h.create(var8);
                              }
            
                              NBTTagCompound var9 = (NBTTagCompound)var8;
                              NBTTagCompound var10 = var9.h();
                              var9.a(var4);
                              var6 += var10.equals(var9) ? 0 : 1;
                           }
            
                           return var6;
                        })))
               )
            );
      }

      var0.register(var1);
   }

   private static String a(NBTBase var0) throws CommandSyntaxException {
      if (var0.c().d()) {
         return var0.f_();
      } else {
         throw i.create(var0);
      }
   }

   private static List<NBTBase> a(List<NBTBase> var0, Function<String, String> var1) throws CommandSyntaxException {
      List<NBTBase> var2 = new ArrayList<>(var0.size());

      for(NBTBase var4 : var0) {
         String var5 = a(var4);
         var2.add(NBTTagString.a(var1.apply(var5)));
      }

      return var2;
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(BiConsumer<ArgumentBuilder<CommandListenerWrapper, ?>, CommandData.b> var0) {
      LiteralArgumentBuilder<CommandListenerWrapper> var1 = net.minecraft.commands.CommandDispatcher.a("modify");

      for(CommandData.c var3 : b) {
         var3.a(
            var1,
            var2 -> {
               ArgumentBuilder<CommandListenerWrapper, ?> var3x = net.minecraft.commands.CommandDispatcher.a("targetPath", ArgumentNBTKey.a());
   
               for(CommandData.c var5 : c) {
                  var0.accept(
                     var3x,
                     var2x -> var5.a(
                           net.minecraft.commands.CommandDispatcher.a("from"),
                           var3xx -> var3xx.executes(var3xxx -> a(var3xxx, var3, var2x, a(var3xxx, var5)))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("sourcePath", ArgumentNBTKey.a())
                                       .executes(var3xxx -> a(var3xxx, var3, var2x, b(var3xxx, var5)))
                                 )
                        )
                  );
                  var0.accept(
                     var3x,
                     var2x -> var5.a(
                           net.minecraft.commands.CommandDispatcher.a("string"),
                           var3xx -> var3xx.executes(var3xxx -> a(var3xxx, var3, var2x, a(a(var3xxx, var5), var0xxxxx -> var0xxxxx)))
                                 .then(
                                    ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("sourcePath", ArgumentNBTKey.a())
                                          .executes(var3xxx -> a(var3xxx, var3, var2x, a(b(var3xxx, var5), var0xxxxx -> var0xxxxx))))
                                       .then(
                                          ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("start", IntegerArgumentType.integer(0))
                                                .executes(
                                                   var3xxx -> a(
                                                         var3xxx,
                                                         var3,
                                                         var2x,
                                                         a(
                                                            b(var3xxx, var5),
                                                            var1xxxxx -> var1xxxxx.substring(IntegerArgumentType.getInteger(var3xxx, "start"))
                                                         )
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("end", IntegerArgumentType.integer(0))
                                                   .executes(
                                                      var3xxx -> a(
                                                            var3xxx,
                                                            var3,
                                                            var2x,
                                                            a(
                                                               b(var3xxx, var5),
                                                               var1xxxxx -> var1xxxxx.substring(
                                                                     IntegerArgumentType.getInteger(var3xxx, "start"),
                                                                     IntegerArgumentType.getInteger(var3xxx, "end")
                                                                  )
                                                            )
                                                         )
                                                   )
                                             )
                                       )
                                 )
                        )
                  );
               }
   
               var0.accept(
                  var3x,
                  var1xx -> net.minecraft.commands.CommandDispatcher.a("value")
                        .then(net.minecraft.commands.CommandDispatcher.a("value", ArgumentNBTBase.a()).executes(var2x -> {
                           List<NBTBase> var3xx = Collections.singletonList(ArgumentNBTBase.a(var2x, "value"));
                           return a(var2x, var3, var1xx, var3xx);
                        }))
               );
               return var2.then(var3x);
            }
         );
      }

      return var1;
   }

   private static List<NBTBase> a(CommandContext<CommandListenerWrapper> var0, CommandData.c var1) throws CommandSyntaxException {
      CommandDataAccessor var2 = var1.a(var0);
      return Collections.singletonList(var2.a());
   }

   private static List<NBTBase> b(CommandContext<CommandListenerWrapper> var0, CommandData.c var1) throws CommandSyntaxException {
      CommandDataAccessor var2 = var1.a(var0);
      ArgumentNBTKey.g var3 = ArgumentNBTKey.a(var0, "sourcePath");
      return var3.a(var2.a());
   }

   private static int a(CommandContext<CommandListenerWrapper> var0, CommandData.c var1, CommandData.a var2, List<NBTBase> var3) throws CommandSyntaxException {
      CommandDataAccessor var4 = var1.a(var0);
      ArgumentNBTKey.g var5 = ArgumentNBTKey.a(var0, "targetPath");
      NBTTagCompound var6 = var4.a();
      int var7 = var2.modify(var0, var6, var5, var3);
      if (var7 == 0) {
         throw d.create();
      } else {
         var4.a(var6);
         ((CommandListenerWrapper)var0.getSource()).a(var4.b(), true);
         return var7;
      }
   }

   private static int a(CommandListenerWrapper var0, CommandDataAccessor var1, ArgumentNBTKey.g var2) throws CommandSyntaxException {
      NBTTagCompound var3 = var1.a();
      int var4 = var2.c(var3);
      if (var4 == 0) {
         throw d.create();
      } else {
         var1.a(var3);
         var0.a(var1.b(), true);
         return var4;
      }
   }

   private static NBTBase a(ArgumentNBTKey.g var0, CommandDataAccessor var1) throws CommandSyntaxException {
      Collection<NBTBase> var2 = var0.a(var1.a());
      Iterator<NBTBase> var3 = var2.iterator();
      NBTBase var4 = var3.next();
      if (var3.hasNext()) {
         throw g.create();
      } else {
         return var4;
      }
   }

   private static int b(CommandListenerWrapper var0, CommandDataAccessor var1, ArgumentNBTKey.g var2) throws CommandSyntaxException {
      NBTBase var3 = a(var2, var1);
      int var4;
      if (var3 instanceof NBTNumber) {
         var4 = MathHelper.a(((NBTNumber)var3).j());
      } else if (var3 instanceof NBTList) {
         var4 = ((NBTList)var3).size();
      } else if (var3 instanceof NBTTagCompound) {
         var4 = ((NBTTagCompound)var3).f();
      } else {
         if (!(var3 instanceof NBTTagString)) {
            throw f.create(var2.toString());
         }

         var4 = var3.f_().length();
      }

      var0.a(var1.a(var3), false);
      return var4;
   }

   private static int a(CommandListenerWrapper var0, CommandDataAccessor var1, ArgumentNBTKey.g var2, double var3) throws CommandSyntaxException {
      NBTBase var5 = a(var2, var1);
      if (!(var5 instanceof NBTNumber)) {
         throw e.create(var2.toString());
      } else {
         int var6 = MathHelper.a(((NBTNumber)var5).j() * var3);
         var0.a(var1.a(var2, var3, var6), false);
         return var6;
      }
   }

   private static int a(CommandListenerWrapper var0, CommandDataAccessor var1) throws CommandSyntaxException {
      var0.a(var1.a((NBTBase)var1.a()), false);
      return 1;
   }

   private static int a(CommandListenerWrapper var0, CommandDataAccessor var1, NBTTagCompound var2) throws CommandSyntaxException {
      NBTTagCompound var3 = var1.a();
      if (ArgumentNBTKey.g.a(var2, 0)) {
         throw ArgumentNBTKey.b.create();
      } else {
         NBTTagCompound var4 = var3.h().a(var2);
         if (var3.equals(var4)) {
            throw d.create();
         } else {
            var1.a(var4);
            var0.a(var1.b(), true);
            return 1;
         }
      }
   }

   interface a {
      int modify(CommandContext<CommandListenerWrapper> var1, NBTTagCompound var2, ArgumentNBTKey.g var3, List<NBTBase> var4) throws CommandSyntaxException;
   }

   interface b {
      ArgumentBuilder<CommandListenerWrapper, ?> create(CommandData.a var1);
   }

   public interface c {
      CommandDataAccessor a(CommandContext<CommandListenerWrapper> var1) throws CommandSyntaxException;

      ArgumentBuilder<CommandListenerWrapper, ?> a(
         ArgumentBuilder<CommandListenerWrapper, ?> var1,
         Function<ArgumentBuilder<CommandListenerWrapper, ?>, ArgumentBuilder<CommandListenerWrapper, ?>> var2
      );
   }
}
