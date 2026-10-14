package sh.orbis;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import sh.orbis.orbzones.OrbZonesPlugin;
import sh.orbis.orbzones.api.ZoneListener;
import sh.orbis.orbzones.api.ZoneListenerRegistration;
import sh.orbis.orbzones.api.ZoneTransitionEvent;
import sh.orbis.ui.Hud;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class OrbEconomyPlugin extends JavaPlugin {
    private static final String BANK_ZONE = "Bank";

    private ZoneListenerRegistration zoneListener;

    public OrbEconomyPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }


    @Override
    protected void setup() {
        zoneListener = OrbZonesPlugin.get()
                        .getZoneManager()
                                .registerListener(new ZoneListener() {
                                    @Override
                                    public void onEnter(@NonNullDecl ZoneTransitionEvent event) {
                                        if (!event.zone().name().equalsIgnoreCase(BANK_ZONE)) {
                                            return;
                                        }

                                        event.player().sendMessage(Message.raw("Welcome to the bank!"));
                                    }

                                    @Override
                                    public void onLeave(@NonNullDecl ZoneTransitionEvent event) {
                                        if (!event.zone().name().equalsIgnoreCase(BANK_ZONE)) {
                                            return;
                                        }

                                        event.player().sendMessage(Message.raw("You have left the bank."));
                                    }
                                });

        getEventRegistry().registerGlobal(PlayerReadyEvent.class, event -> {
            Ref<EntityStore> playerEntity = event.getPlayerRef();
            Store<EntityStore> store = playerEntity.getStore();
            PlayerRef playerRef = store.getComponent(playerEntity, PlayerRef.getComponentType());

            if (playerRef == null) {
                getLogger().at(Level.WARNING).log("PlayerRef is null for player: %s", playerEntity);
                return;
            }

            event.getPlayer().getHudManager().addCustomHud(playerRef, new Hud(playerRef));
        });
    }

    @Override
    protected void shutdown() {
        if (zoneListener != null) {
            zoneListener.close();
        }
    }
}
