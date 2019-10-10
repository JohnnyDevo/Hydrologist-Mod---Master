package hydrologistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import helpers.CardProxyHelper;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;

public class ProxyPatch {

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class AbstractCardMakeStatEquivalentCopyPatch {

        @SpireInsertPatch(
                rloc = 1
        )
        public static void Insert(AbstractCard __instance, @ByRef AbstractCard[] ___card) {
            if (CardProxyHelper.isCreatingProxy) {
                CardProxyHelper.isCreatingProxy = false;
                ProxyFactory f = new ProxyFactory();
                f.setSuperclass(__instance.getClass());
                f.setFilter(CardProxyHelper.storedFilter);
                CardProxyHelper.storedFilter = null;
                try {
                    ___card[0] = (AbstractCard) f.create(new Class[0], new Object[0], CardProxyHelper.storedHandler);
                } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    System.out.println("ERROR: Proxy class creation failed!");
                    e.printStackTrace();
                }
                CardProxyHelper.storedHandler = null;
            }
        }
    }
}