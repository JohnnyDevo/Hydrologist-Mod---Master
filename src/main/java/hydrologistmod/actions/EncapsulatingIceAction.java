package hydrologistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hydrologistmod.powers.EncapsulatingIcePower;
import hydrologistmod.vfx.EncapsulatingIceEffect;

public class EncapsulatingIceAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private int energyOnUse;
    private boolean freeToPlayOnce;
    private boolean upgraded;

    public EncapsulatingIceAction(int energyOnUse, boolean upgraded, boolean freeToPlayOnce) {
        this.duration = DURATION;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.effectList.add(new EncapsulatingIceEffect(p.hb.cX, p.hb.cY, 1.0f, 0.7f, 0.5f));
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (upgraded) {
            ++effect;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (effect > 0) {
            addToTop(new ApplyPowerAction(p, p, new EncapsulatingIcePower(p, effect), effect));
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }

        isDone = true;
    }
}