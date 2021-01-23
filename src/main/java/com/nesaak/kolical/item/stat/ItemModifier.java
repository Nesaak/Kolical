package com.nesaak.kolical.item.stat;

import com.nesaak.kolical.util.modifier.Modifier;
import com.nesaak.kolical.util.modifier.Oper;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.chat.ColoredText;
import net.minestom.server.chat.JsonMessage;
import net.minestom.server.chat.RichMessage;

public class ItemModifier extends Modifier {

    protected ItemStat stat;

    public ItemModifier(Oper operation, ItemStat stat, double value) {
        super(operation, value);
        this.stat = stat;
    }

    public ItemStat getStat() {
        return stat;
    }

    public JsonMessage getDisplay() {
        ChatColor color = value > 0 ? ChatColor.BRIGHT_GREEN : ChatColor.RED;
        String sign = value > 0 ? "+" : "";
        String modifier = operation == Oper.ADD ? "" : operation == Oper.MULTIPLY ? "%" : "^";
        RichMessage message = RichMessage.of(ColoredText.of(""));
        message.append(ColoredText.of(color, sign + value + modifier + " "));
        message.append(ColoredText.of(ChatColor.GRAY, stat.getName()));
        return message;
    }

}


