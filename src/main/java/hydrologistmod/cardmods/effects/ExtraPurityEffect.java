package hydrologistmod.cardmods.effects;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import hydrologistmod.cardmods.PurityModifier;

import java.util.ArrayList;

@AbstractCardModifier.SaveIgnore
public class ExtraPurityEffect extends AbstractExtraEffectModifier {
    private static final String ID = "hydrologistmod:ExtraPurityEffect";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] TEXT = cardStrings.EXTENDED_DESCRIPTION;

    public ExtraPurityEffect(AbstractCard card, boolean isMutable, int times) {
        super(card, VariableType.MAGIC, isMutable, times);
        priority = 1;
    }

    @Override
    public void doExtraEffects(AbstractCard card, AbstractPlayer p, AbstractCreature m) {

    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            ArrayList<AbstractCardModifier> list = CardModifierManager.getModifiers(card, ID);
            for (AbstractCardModifier mod : list) {
                AbstractCard c = ((AbstractExtraEffectModifier)mod).attachedCard;
                if (c.baseMagicNumber == attachedCard.baseMagicNumber) {
                    ((AbstractExtraEffectModifier)mod).amount++;
                    card.applyPowers();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        super.onApplyPowers(card);
        baseValue *= amount;
        value *= amount;
    }

    @Override
    public boolean shouldRenderValue() {
        return true;
    }

    @Override
    public String addExtraText(String rawDescription, AbstractCard card) {
        String s =  TEXT[0] + key + TEXT[1];
        s = applyMutable(s);
        return rawDescription + " NL " + s;
    }

    @Override
    public void onCardTransmuted(AbstractCard oldCard, AbstractCard newCard, boolean firstTime) {
        CardModifierManager.addModifier(newCard, new PurityModifier(value));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExtraPurityEffect(attachedCard, isMutable, amount);
    }
}
