/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.core.base;

import me.filoghost.holographicdisplays.core.CorePreconditions;
import me.filoghost.holographicdisplays.nms.common.entity.TextNMSPacketEntity;
import org.jetbrains.annotations.Nullable;

public abstract class BaseTextHologramLine extends BaseClickableHologramLine {

    private String text;
    private boolean sneaking;

    public BaseTextHologramLine(BaseHologram hologram, String text) {
        super(hologram);
        setText(text);
        setSneaking(false);
    }

    public abstract boolean isAllowPlaceholders();

    public @Nullable String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        CorePreconditions.checkMainThread();
        checkNotDeleted();

        this.text = text;
        setChanged();
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        checkNotDeleted();

        this.sneaking = sneaking;
        setChanged();
    }

    @Override
    public double getHeight() {
        return TextNMSPacketEntity.ARMOR_STAND_TEXT_HEIGHT;
    }

    @Override
    public String toString() {
        return "TextLine{"
                + "text=" + text
                + "}";
    }

}
