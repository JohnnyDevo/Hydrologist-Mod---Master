package hydrologistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hydrologistmod.actions.ApplyThermalShockAction;
import hydrologistmod.patches.AbstractCardEnum;
import hydrologistmod.patches.HydrologistTags;

public class Debilitate extends AbstractHydrologistCard {
    public static final String ID = "hydrologistmod:Debilitate";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "hydrologistmod/images/cards/Debilitate.png";
    private static final int COST = 2;
    private static final int AMOUNT_TO_APPLY = 1;
    private static final int UPGRADE_AMOUNT = 1;
    private static final int DAMAGE_AMT = 12;
    private static final int UPGRADE_DAMAGE = 3;

    public Debilitate() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.HYDROLOGIST_CYAN,
                CardRarity.COMMON, CardTarget.ALL_ENEMY);
        assignHydrologistSubtype(HydrologistTags.WATER);
        magicNumber = baseMagicNumber = AMOUNT_TO_APPLY;
        exhaust = true;
        damage = baseDamage = DAMAGE_AMT;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyThermalShockAction(mo, p, magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Debilitate();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_AMOUNT);
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
