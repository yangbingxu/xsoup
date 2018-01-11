package us.codecraft.xsoup.xevaluator;

/**
 * Created by Admin on 2018/1/1.
 */
public class SingletonProducer {
    private static SingletonProducer producer = new SingletonProducer();
    private AxisSelector axisSelector = new AxisSelector();
    public static SingletonProducer getInstance() {
        return producer;
    }
    public AxisSelector getAxisSelector() {
        return axisSelector;
    }
    private SingletonProducer() {
    }
}
