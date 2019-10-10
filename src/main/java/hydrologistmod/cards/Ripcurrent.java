package hydrologistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import helpers.SwapperHelper;
import hydrologistmod.actions.MultiplyPowerAction;
import hydrologistmod.interfaces.SwappableCard;
import hydrologistmod.patches.AbstractCardEnum;
import hydrologistmod.patches.HydrologistTags;

public class Ripcurrent extends AbstractHydrologistCard implements SwappableCard {
    public static final String ID = "hydrologistmod:Ripcurrent";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "hydrologistmod/images/cards/Ripcurrent.png";
    private static final int COST = 1;
    private static final int MULTIPLIER = 2;
    private static final int UPGRADE_MULTIPLIER = 1;

    public Ripcurrent() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.HYDROLOGIST_CYAN,
                CardRarity.UNCOMMON, CardTarget.ENEMY);
        tags.add(HydrologistTags.WATER);
        magicNumber = baseMagicNumber = MULTIPLIER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(WeakPower.POWER_ID)) {
            addToBot(new MultiplyPowerAction(m.getPower(WeakPower.POWER_ID), m, p, magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ripcurrent();
    }

    @Override
    public void upgrade() {
        SwapperHelper.upgrade(this);
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MULTIPLIER);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public boolean hasDefaultPair() {
        return true;
    }

    @Override
    public AbstractCard createDefaultPair() {
        return new HeatAndPressure();
    }
}