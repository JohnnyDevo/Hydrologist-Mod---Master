package hydrologistmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hydrologistmod.actions.TransmuteCardAction;
import hydrologistmod.patches.HydrologistTags;

public class EvaporationPower extends AbstractPower implements NonStackablePower, CloneablePowerInterface {
    public static final String POWER_ID = "hydrologistmod:EvaporationPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EvaporationPower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("hydrologistmod/images/powers/EvaporationPower84.png"), 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("hydrologistmod/images/powers/EvaporationPower32.png"), 0, 0, 32, 32);
        type = PowerType.BUFF;
        this.amount = amount;
        updateDescription();
    }

    @Override
    public void updateDescription() {

    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        if (card.hasTag(HydrologistTags.STEAM)) {
            flash();
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, owner, new VulnerablePower(m, amount, false), amount));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new EvaporationPower(owner, amount);
    }
}
