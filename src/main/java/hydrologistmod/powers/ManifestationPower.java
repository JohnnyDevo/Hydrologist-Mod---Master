package hydrologistmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hydrologistmod.HydrologistMod;
import hydrologistmod.interfaces.CorporealRelevantPower;

public class ManifestationPower extends AbstractPower implements CloneablePowerInterface, CorporealRelevantPower {
    public static final String POWER_ID = "hydrologistmod:ManifestationPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ManifestationPower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("hydrologistmod/images/powers/ManifestationPower84.png"), 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("hydrologistmod/images/powers/ManifestationPower32.png"), 0, 0, 32, 32);
        type = PowerType.BUFF;
        this.amount = amount;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (HydrologistMod.isThisCorporeal(card)) {
            damage += amount;
        }
        return damage;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        if (HydrologistMod.isThisCorporeal(card)) {
            block += amount;
        }
        return block;
    }

    @Override
    public AbstractPower makeCopy() {
        return new ManifestationPower(owner, amount);
    }

    @Override
    public boolean activateGlow(AbstractCard card) {
        return (card.baseBlock > -1 || card.baseDamage > -1);
    }
}
