package kr.jm.utils;

import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMLambda;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JavascriptEvaluator {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JavascriptEvaluator.class);

    private static ScriptEngine scriptEngine;


    public static Object eval(String script) {
        try {
            return getScriptEngine().eval(script);
        } catch (Exception e) {
            return JMExceptionManager
                    .handleExceptionAndReturnNull(log, e, "eval", script);
        }
    }

    private static ScriptEngine getScriptEngine() {
        return JMLambda.supplierIfNull(scriptEngine, () -> scriptEngine =
                new ScriptEngineManager().getEngineByName("JavaScript"));
    }

}
