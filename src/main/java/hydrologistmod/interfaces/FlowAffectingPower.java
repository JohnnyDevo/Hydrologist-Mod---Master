package hydrologistmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public interface FlowAffectingPower {
    default void onFlow(ArrayList<AbstractCard> cardsDiscarded) {}
}
