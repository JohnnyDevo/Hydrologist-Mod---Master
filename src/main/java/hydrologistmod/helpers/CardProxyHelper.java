package hydrologistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;

public class CardProxyHelper {
    public static boolean isCreatingProxy = false;
    public static MethodHandler storedHandler;
    public static MethodFilter storedFilter;

    public static AbstractCard createSameInstanceProxy(AbstractCard card, MethodFilter filter, MethodHandler newBehaviour) {
        isCreatingProxy = true;
        storedHandler = newBehaviour;
        storedFilter = filter;
        return card.makeSameInstanceOf();
    }

    public static AbstractCard createStatEquivalentProxy(AbstractCard card, MethodFilter filter, MethodHandler newBehaviour) {
        isCreatingProxy = true;
        storedHandler = newBehaviour;
        storedFilter = filter;
        return card.makeStatEquivalentCopy();
    }
}
