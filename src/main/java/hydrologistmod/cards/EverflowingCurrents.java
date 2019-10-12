package hydrologistmod.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helpers.SwapperHelper;
import hydrologistmod.actions.FlowAction;
import hydrologistmod.interfaces.SwappableCard;
import hydrologistmod.patches.AbstractCardEnum;
import hydrologistmod.patches.HydrologistTags;

public class EverflowingCurrents extends AbstractHydrologistCard implements SwappableCard {
    public static final String ID = "hydrologistmod:EverflowingCurrents";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "hydrologistmod/images/cards/EverflowingCurrents.png";
    private static final int COST = 0;
    private static final int DRAW = 1;
    private int lastTurnSwapped = -1;

    public EverflowingCurrents() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.HYDROLOGIST_CYAN,
                CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(HydrologistTags.WATER);
        magicNumber = baseMagicNumber = DRAW;
        SwapperHelper.registerPair(this, createDefaultPair());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new DrawCardAction(p, magicNumber));
        }
        addToBot(new FlowAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new EverflowingCurrents();
    }

    @Override
    public void upgrade() {
        SwapperHelper.upgrade(this);
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public boolean canSwap() {
        return GameActionManager.turn > lastTurnSwapped;
    }

    @Override
    public void onSwapOut() {
        lastTurnSwapped = GameActionManager.turn;
    }

    @Override
    public boolean hasDefaultPair() {
        return true;
    }

    @Override
    public AbstractCard createDefaultPair() {
        return new GlacierBash();
    }
}
