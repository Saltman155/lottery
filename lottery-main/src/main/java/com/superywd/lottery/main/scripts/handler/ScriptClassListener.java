package com.superywd.lottery.main.scripts.handler;

import com.superywd.library.script.classlistener.ClassListener;
import com.superywd.library.utils.ClassUtil;
import com.superywd.lottery.main.scripts.AbstractScript;
import com.superywd.lottery.main.scripts.ScriptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;

/**
 *
 * @author 迷宫的中心
 * @date 2019/5/15 22:09
 */
public class ScriptClassListener implements ClassListener {

    private static final Logger logger = LoggerFactory.getLogger(ScriptClassListener.class);

    @SuppressWarnings("unchecked")
    @Override
    public void postLoad(Class<?>[] classes) {
        for(Class<?> clazz : classes){
            if (!isValidClass(clazz)) {
                continue;
            }
            if (ClassUtil.isSubclass(clazz, AbstractScript.class)) {
                Class<? extends AbstractScript> tmp = (Class<? extends AbstractScript>) clazz;
                if (tmp != null) {
                    ScriptService.getInstance().registerClass(tmp);
                }
            }
        }
    }

    @Override
    public void preUnload(Class<?>[] classes) {
        //什么也不干
    }

    public boolean isValidClass(Class<?> clazz) {
        final int modifiers = clazz.getModifiers();
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
            return false;
        }
        if (!Modifier.isPublic(modifiers)) {
            return false;
        }
        return true;
    }
}
