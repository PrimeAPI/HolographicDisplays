/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.core.placeholder;

import org.bukkit.entity.Player;

public interface RelativePlaceholderReplacer {
    
    String getReplacement(Player player);

}
