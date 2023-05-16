package org.bukkit.craftbukkit.v1_19_R3.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatHexColor;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.bukkit.ChatColor;

public final class CraftChatMessage {
   private static final Pattern LINK_PATTERN = Pattern.compile(
      "((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))"
   );
   private static final Map<Character, EnumChatFormat> formatMap;

   static {
      Builder<Character, EnumChatFormat> builder = ImmutableMap.builder();

      EnumChatFormat[] var4;
      for(EnumChatFormat format : var4 = EnumChatFormat.values()) {
         builder.put(Character.toLowerCase(format.toString().charAt(1)), format);
      }

      formatMap = builder.build();
   }

   public static EnumChatFormat getColor(ChatColor color) {
      return formatMap.get(color.getChar());
   }

   public static ChatColor getColor(EnumChatFormat format) {
      return ChatColor.getByChar(format.B);
   }

   public static IChatBaseComponent fromStringOrNull(String message) {
      return fromStringOrNull(message, false);
   }

   public static IChatBaseComponent fromStringOrNull(String message, boolean keepNewlines) {
      return message != null && !message.isEmpty() ? fromString(message, keepNewlines)[0] : null;
   }

   public static IChatBaseComponent[] fromString(String message) {
      return fromString(message, false);
   }

   public static IChatBaseComponent[] fromString(String message, boolean keepNewlines) {
      return fromString(message, keepNewlines, false);
   }

   public static IChatBaseComponent[] fromString(String message, boolean keepNewlines, boolean plain) {
      return new CraftChatMessage.StringMessage(message, keepNewlines, plain).getOutput();
   }

   public static String toJSON(IChatBaseComponent component) {
      return IChatBaseComponent.ChatSerializer.a(component);
   }

   public static String toJSONOrNull(IChatBaseComponent component) {
      return component == null ? null : toJSON(component);
   }

   public static IChatBaseComponent fromJSON(String jsonMessage) throws JsonParseException {
      return IChatBaseComponent.ChatSerializer.a(jsonMessage);
   }

   public static IChatBaseComponent fromJSONOrNull(String jsonMessage) {
      if (jsonMessage == null) {
         return null;
      } else {
         try {
            return fromJSON(jsonMessage);
         } catch (JsonParseException var2) {
            return null;
         }
      }
   }

   public static IChatBaseComponent fromJSONOrString(String message) {
      return fromJSONOrString(message, false);
   }

   public static IChatBaseComponent fromJSONOrString(String message, boolean keepNewlines) {
      return fromJSONOrString(message, false, keepNewlines);
   }

   private static IChatBaseComponent fromJSONOrString(String message, boolean nullable, boolean keepNewlines) {
      if (message == null) {
         message = "";
      }

      if (nullable && message.isEmpty()) {
         return null;
      } else {
         IChatBaseComponent component = fromJSONOrNull(message);
         return component != null ? component : fromString(message, keepNewlines)[0];
      }
   }

   public static String fromJSONOrStringToJSON(String message) {
      return fromJSONOrStringToJSON(message, false);
   }

   public static String fromJSONOrStringToJSON(String message, boolean keepNewlines) {
      return fromJSONOrStringToJSON(message, false, keepNewlines, Integer.MAX_VALUE, false);
   }

   public static String fromJSONOrStringOrNullToJSON(String message) {
      return fromJSONOrStringOrNullToJSON(message, false);
   }

   public static String fromJSONOrStringOrNullToJSON(String message, boolean keepNewlines) {
      return fromJSONOrStringToJSON(message, true, keepNewlines, Integer.MAX_VALUE, false);
   }

   public static String fromJSONOrStringToJSON(String message, boolean nullable, boolean keepNewlines, int maxLength, boolean checkJsonContentLength) {
      if (message == null) {
         message = "";
      }

      if (nullable && message.isEmpty()) {
         return null;
      } else {
         IChatBaseComponent component = fromJSONOrNull(message);
         if (component != null) {
            if (checkJsonContentLength) {
               String content = fromComponent(component);
               String trimmedContent = trimMessage(content, maxLength);
               if (content != trimmedContent) {
                  return fromStringToJSON(trimmedContent, keepNewlines);
               }
            }

            return message;
         } else {
            message = trimMessage(message, maxLength);
            return fromStringToJSON(message, keepNewlines);
         }
      }
   }

   public static String trimMessage(String message, int maxLength) {
      return message != null && message.length() > maxLength ? message.substring(0, maxLength) : message;
   }

   public static String fromStringToJSON(String message) {
      return fromStringToJSON(message, false);
   }

   public static String fromStringToJSON(String message, boolean keepNewlines) {
      IChatBaseComponent component = fromString(message, keepNewlines)[0];
      return toJSON(component);
   }

   public static String fromStringOrNullToJSON(String message) {
      IChatBaseComponent component = fromStringOrNull(message);
      return toJSONOrNull(component);
   }

   public static String fromJSONComponent(String jsonMessage) {
      IChatBaseComponent component = fromJSONOrNull(jsonMessage);
      return fromComponent(component);
   }

   public static String fromComponent(IChatBaseComponent component) {
      if (component == null) {
         return "";
      } else {
         StringBuilder out = new StringBuilder();
         boolean hadFormat = false;

         for(IChatBaseComponent c : component) {
            ChatModifier modi = c.a();
            ChatHexColor color = modi.a();
            if (c.b() != ComponentContents.a || color != null) {
               if (color == null) {
                  if (hadFormat) {
                     out.append(ChatColor.RESET);
                     hadFormat = false;
                  }
               } else {
                  if (color.format != null) {
                     out.append(color.format);
                  } else {
                     out.append('§').append("x");

                     char[] var10;
                     for(char magic : var10 = color.b().substring(1).toCharArray()) {
                        out.append('§').append(magic);
                     }
                  }

                  hadFormat = true;
               }
            }

            if (modi.b()) {
               out.append(EnumChatFormat.r);
               hadFormat = true;
            }

            if (modi.c()) {
               out.append(EnumChatFormat.u);
               hadFormat = true;
            }

            if (modi.e()) {
               out.append(EnumChatFormat.t);
               hadFormat = true;
            }

            if (modi.d()) {
               out.append(EnumChatFormat.s);
               hadFormat = true;
            }

            if (modi.f()) {
               out.append(EnumChatFormat.q);
               hadFormat = true;
            }

            c.b().a(x -> {
               out.append(x);
               return Optional.empty();
            });
         }

         return out.toString();
      }
   }

   public static IChatBaseComponent fixComponent(IChatMutableComponent component) {
      Matcher matcher = LINK_PATTERN.matcher("");
      return fixComponent(component, matcher);
   }

   private static IChatBaseComponent fixComponent(IChatMutableComponent component, Matcher matcher) {
      if (component.b() instanceof LiteralContents text) {
         String msg = text.a();
         if (matcher.reset(msg).find()) {
            matcher.reset();
            ChatModifier modifier = component.a();
            List<IChatBaseComponent> extras = new ArrayList<>();
            List<IChatBaseComponent> extrasOld = new ArrayList<>(component.c());
            component = IChatBaseComponent.h();

            int pos;
            for(pos = 0; matcher.find(); pos = matcher.end()) {
               String match = matcher.group();
               if (!match.startsWith("http://") && !match.startsWith("https://")) {
                  match = "http://" + match;
               }

               IChatMutableComponent prev = IChatBaseComponent.b(msg.substring(pos, matcher.start()));
               prev.b(modifier);
               extras.add(prev);
               IChatMutableComponent link = IChatBaseComponent.b(matcher.group());
               ChatModifier linkModi = modifier.a(new ChatClickable(ChatClickable.EnumClickAction.a, match));
               link.b(linkModi);
               extras.add(link);
            }

            IChatMutableComponent prev = IChatBaseComponent.b(msg.substring(pos));
            prev.b(modifier);
            extras.add(prev);
            extras.addAll(extrasOld);

            for(IChatBaseComponent c : extras) {
               component.b(c);
            }
         }
      }

      List<IChatBaseComponent> extras = component.c();

      for(int i = 0; i < extras.size(); ++i) {
         IChatBaseComponent comp = extras.get(i);
         if (comp.a() != null && comp.a().h() == null) {
            extras.set(i, fixComponent(comp.e(), matcher));
         }
      }

      if (component.b() instanceof TranslatableContents) {
         Object[] subs = ((TranslatableContents)component.b()).c();

         for(int i = 0; i < subs.length; ++i) {
            Object comp = subs[i];
            if (comp instanceof IChatBaseComponent c) {
               if (c.a() != null && c.a().h() == null) {
                  subs[i] = fixComponent(c.e(), matcher);
               }
            } else if (comp instanceof String && matcher.reset((String)comp).find()) {
               subs[i] = fixComponent(IChatBaseComponent.b((String)comp), matcher);
            }
         }
      }

      return component;
   }

   private CraftChatMessage() {
   }

   private static final class StringMessage {
      private static final Pattern INCREMENTAL_PATTERN = Pattern.compile(
         "(" + String.valueOf('§') + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + 167 + " \\n]|$))))|(\\n)",
         2
      );
      private static final Pattern INCREMENTAL_PATTERN_KEEP_NEWLINES = Pattern.compile(
         "(" + String.valueOf('§') + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + 167 + " ]|$))))", 2
      );
      private static final ChatModifier RESET = ChatModifier.a.a(false).b(false).c(false).d(false).e(false);
      private final List<IChatBaseComponent> list = new ArrayList<>();
      private IChatMutableComponent currentChatComponent = IChatBaseComponent.h();
      private ChatModifier modifier = ChatModifier.a;
      private final IChatBaseComponent[] output;
      private int currentIndex;
      private StringBuilder hex;
      private final String message;

      private StringMessage(String message, boolean keepNewlines, boolean plain) {
         this.message = message;
         if (message == null) {
            this.output = new IChatBaseComponent[]{this.currentChatComponent};
         } else {
            this.list.add(this.currentChatComponent);
            Matcher matcher = (keepNewlines ? INCREMENTAL_PATTERN_KEEP_NEWLINES : INCREMENTAL_PATTERN).matcher(message);
            String match = null;

            boolean needsAdd;
            int groupId;
            for(needsAdd = false; matcher.find(); this.currentIndex = matcher.end(groupId)) {
               groupId = 0;

               while((match = matcher.group(++groupId)) == null) {
               }

               int index = matcher.start(groupId);
               if (index > this.currentIndex) {
                  needsAdd = false;
                  this.appendNewComponent(index);
               }

               switch(groupId) {
                  case 1:
                     char c = match.toLowerCase(Locale.ENGLISH).charAt(1);
                     EnumChatFormat format = CraftChatMessage.formatMap.get(c);
                     if (c == 'x') {
                        this.hex = new StringBuilder("#");
                     } else if (this.hex != null) {
                        this.hex.append(c);
                        if (this.hex.length() == 7) {
                           this.modifier = RESET.a(ChatHexColor.a(this.hex.toString()));
                           this.hex = null;
                        }
                     } else if (format.d() && format != EnumChatFormat.v) {
                        switch(format) {
                           case q:
                              this.modifier = this.modifier.e(Boolean.TRUE);
                              break;
                           case r:
                              this.modifier = this.modifier.a(Boolean.TRUE);
                              break;
                           case s:
                              this.modifier = this.modifier.d(Boolean.TRUE);
                              break;
                           case t:
                              this.modifier = this.modifier.c(Boolean.TRUE);
                              break;
                           case u:
                              this.modifier = this.modifier.b(Boolean.TRUE);
                              break;
                           default:
                              throw new AssertionError("Unexpected message format");
                        }
                     } else {
                        this.modifier = RESET.a(format);
                     }

                     needsAdd = true;
                     break;
                  case 2:
                     if (plain) {
                        this.appendNewComponent(matcher.end(groupId));
                     } else {
                        if (!match.startsWith("http://") && !match.startsWith("https://")) {
                           match = "http://" + match;
                        }

                        this.modifier = this.modifier.a(new ChatClickable(ChatClickable.EnumClickAction.a, match));
                        this.appendNewComponent(matcher.end(groupId));
                        this.modifier = this.modifier.a(null);
                     }
                     break;
                  case 3:
                     if (needsAdd) {
                        this.appendNewComponent(index);
                     }

                     this.currentChatComponent = null;
               }
            }

            if (this.currentIndex < message.length() || needsAdd) {
               this.appendNewComponent(message.length());
            }

            this.output = this.list.toArray(new IChatBaseComponent[this.list.size()]);
         }
      }

      private void appendNewComponent(int index) {
         IChatBaseComponent addition = IChatBaseComponent.b(this.message.substring(this.currentIndex, index)).b(this.modifier);
         this.currentIndex = index;
         if (this.currentChatComponent == null) {
            this.currentChatComponent = IChatBaseComponent.h();
            this.list.add(this.currentChatComponent);
         }

         this.currentChatComponent.b(addition);
      }

      private IChatBaseComponent[] getOutput() {
         return this.output;
      }
   }
}
