package hydrologistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hydrologistmod.helpers.SwapperHelper;
import hydrologistmod.CardIgnore;
import hydrologistmod.actions.TransmuteCardAction;
import hydrologistmod.patches.AbstractCardEnum;
import hydrologistmod.patches.HydrologistTags;

@CardIgnore
public class SeaFoam extends AbstractHydrologistCard {
    public static final String ID = "hydrologistmod:SeaFoam";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "hydrologistmod/images/cards/SeaFoam.png";
    private static final int COST = 1;
    private static final int DAMAGE_AMT = 7;
    private static final int UPGRADE_DAMAGE = 3;

    public SeaFoam() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.HYDROLOGIST_CYAN,
                CardRarity.SPECIAL, CardTarget.ENEMY);
        tags.add(HydrologistTags.STEAM);
        damage = baseDamage = DAMAGE_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new TransmuteCardAction(false));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SeaFoam();
    }

    @Override
    public void upgrade() {
        SwapperHelper.upgrade(this);
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
