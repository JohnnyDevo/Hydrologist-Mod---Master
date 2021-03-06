package hydrologistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UpstreamAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private AbstractMonster monster;
    private int blockAmt;

    public UpstreamAction(AbstractMonster monster, int blockAmt) {
        duration = DURATION;
        this.monster = monster;
        this.blockAmt = blockAmt;
    }

    @Override
    public void update() {
        if (monster != null && monster.getIntentBaseDmg() >= 0) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockAmt));
        }
        isDone = true;
    }
}