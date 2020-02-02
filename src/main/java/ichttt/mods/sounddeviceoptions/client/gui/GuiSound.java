/*
 * SoundDeviceOptions
 * Copyright (C) 2018-2019
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ichttt.mods.sounddeviceoptions.client.gui;

import ichttt.mods.sounddeviceoptions.SDOConfig;
import ichttt.mods.sounddeviceoptions.client.SoundDevices;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.ErrorScreen;
import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiSound extends OptionsSoundsScreen {
    public GuiSound(Screen parentIn, GameSettings settingsIn) {
        super(parentIn, settingsIn);
    }

    @Override
    public void init() {
        super.init();
//        SoundDevices.reloadDeviceList();
        Widget fromBList = this.buttons.remove(this.buttons.size() - 1);
        IGuiEventListener fromCList = this.children.remove(this.children.size() - 1);
        if (fromBList != fromCList) {
            throw new RuntimeException("Removed wrong button? From button list= " + fromBList + " msg " + fromBList.getMessage() + " from children list= " + fromCList);
        }
        addButton(new Button(this.width / 2 - 100, this.height / 6 + 156, 200, 20, minecraft.fontRenderer.trimStringToWidth(I18n.format("sounddeviceoptions.output", SDOConfig.friendlyActiveSoundDevice()), 200), b -> {
            SoundDevices.reloadDeviceList();
            if (SoundDevices.validDevices.isEmpty()) {
                minecraft.displayGuiScreen(new ErrorScreen(new TranslationTextComponent("sounddeviceoptions.readFailed"), I18n.format("sounddeviceoptions.readFailedHint")));
            } else {
                minecraft.displayGuiScreen(new GuiChooseOutput(GuiSound.this));
            }
        }));
        addButton(new Button(this.width / 2 - 100, this.height / 6 + 180, 200, 20, I18n.format("gui.done"), button -> {
            GuiSound.this.minecraft.gameSettings.saveOptions(); //Same as GuiScreenOptionsSound this
            GuiSound.this.minecraft.displayGuiScreen(GuiSound.this.parent);
        }));
    }
}
