package sh.orbis.ui;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class Hud extends CustomUIHud {

    public Hud(@Nonnull PlayerRef playerRef) {
        super(playerRef, "hud");
    }

    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {

        uiCommandBuilder.append("Hud/Hud.ui");
        uiCommandBuilder.set("#Coins.TextSpans", Message.raw("345"));
    }

    public static class BindingData {
        public static final BuilderCodec<BindingData> CODEC = BuilderCodec.builder(BindingData.class, BindingData::new)
                .build();
    }
}