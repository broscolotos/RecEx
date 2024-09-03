package com.bigbass.recex.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;

import com.bigbass.recex.RecipeExporterMod;
import com.bigbass.recex.graphics.Color;
import com.bigbass.recex.graphics.GraphicsRender;
import com.bigbass.recex.graphics.GraphicsRender.FillType;
import com.bigbass.recex.recipes.RecipeExporter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMain extends GuiScreen {

    private final Color fontColor = new Color(0xFFFFFFFF);
    private final Color background = new Color(0x000000FF);
    private GuiButton exportButton;

    public GuiMain() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        exportButton = new GuiButton(0, 50, 50, 200, 20, "Export!");

        this.buttonList.add(exportButton);
    }

    @Override
    public void drawScreen(int mx, int my, float partTicks) {
        GraphicsRender.rect(0, 0, width, height, background, FillType.FILLED);

        this.fontRendererObj.drawString(
            "Recipe Exporter",
            (this.width / 2) - (this.fontRendererObj.getStringWidth("Recipe Exporter") / 2),
            6,
            fontColor.toARGB());

        super.drawScreen(mx, my, partTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mx, int my, int button) {
        super.mouseClicked(mx, my, button);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.equals(exportButton)) {
            RecipeExporterMod.log.info("Export Button Pressed!");

            this.mc.ingameGUI.getChatGUI()
                .printChatMessage(new ChatComponentText("Started export"));

            Thread thread = new Thread(() -> {
                long start = System.nanoTime();

                try {
                    RecipeExporter.getInst()
                        .run();
                } catch (Throwable t) {
                    t.printStackTrace();
                    mc.getNetHandler()
                        .handleChat(new S02PacketChat(new ChatComponentText("Export threw exception: " + t)));
                }

                mc.getNetHandler()
                    .handleChat(
                        new S02PacketChat(
                            new ChatComponentText(
                                String.format("Finished export in %.2fs", (System.nanoTime() - start) / 1e9))));
            });

            thread.setDaemon(true);
            thread.setName("recipe export thread");

            thread.start();

            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawDefaultBackground() {

    }
}
