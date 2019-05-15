package lottery.script.system;

import com.superywd.lottery.main.scripts.AbstractScript;
import com.superywd.lottery.main.model.TbAward;
import com.superywd.lottery.main.scripts.metadata.ScriptName;

/**
 * 基础的抽奖操作
 * @author 迷宫的中心
 * @date 2019/5/10 22:28
 */


@ScriptName(name="demo")
public class DemoScript extends AbstractScript {

    @Override
    public TbAward draw() {
        TbAward award = new TbAward();
        award.setId(123123);
        return award;
    }

}