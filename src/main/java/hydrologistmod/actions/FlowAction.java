package hydrologistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hydrologistmod.interfaces.FlowAffectingPower;
import hydrologistmod.powers.FlowPower;

import java.util.ArrayList;

public class FlowAction extends AbstractGameAction {
    private static final String ID = "hydrologistmod:FlowAction";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private AfterDiscard followup;

    public FlowAction() {
        duration = DURATION;
        followup = null;
    }

    public FlowAction(AfterDiscard followup) {
        duration = DURATION;
        this.followup = followup;
    }

    @Override
    public void update() {
        if (duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            if (AbstractDungeon.player.hand.size() < 1) {
                isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            }
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            ArrayList<AbstractCard> cardsDiscarded = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.player.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                cardsDiscarded.add(c);
            }
            if (cardsDiscarded.size() > 0) {
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                if (followup != null) {
                    followup.doActions(cardsDiscarded);
                }
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof FlowAffectingPower) {
                        ((FlowAffectingPower) p).onFlow(cardsDiscarded);
                    }
                }
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    for (AbstractPower p : m.powers) {
                        if (p instanceof FlowAffectingPower) {
                            ((FlowAffectingPower) p).onFlow(cardsDiscarded);
                        }
                    }
                }
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FlowPower(AbstractDungeon.player, cardsDiscarded.size())));
            }
        }
        tickDuration();
    }

    public interface AfterDiscard {
        void doActions(ArrayList<AbstractCard> cards);
    }
}