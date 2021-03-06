package hydrologistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JetPropulsionAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private AbstractMonster monster;
    private int drawAmt;

    public JetPropulsionAction(AbstractMonster monster, int drawAmt) {
        duration = DURATION;
        this.monster = monster;
        this.drawAmt = drawAmt;
    }

    @Override
    public void update() {
        if (monster != null && monster.getIntentBaseDmg() <= 0) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, drawAmt));
        }
        isDone = true;
    }
}