package us.codecraft.xsoup.xevaluator;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import us.codecraft.xsoup.StringUtils;
import us.codecraft.xsoup.XElements;
import us.codecraft.xsoup.XPathEvaluator;
import us.codecraft.xsoup.XTokenQueue;
import us.codecraft.xsoup.exception.NoSuchAxisException;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Base structural evaluator.
 * Copy from {@link org.jsoup.select.StructuralEvaluator} because it is package visible
 *
 * @see org.jsoup.select.StructuralEvaluator
 */
public class AxisEvaluator implements XPathEvaluator {
    String subQuery;

    private static Map<String, Method> axisFuncs = new HashMap<String, Method>();
    static{
        for (Method m : AxisSelector.class.getDeclaredMethods()) {
            axisFuncs.put(renderFuncKey(m.getName(), m.getParameterTypes()), m);
        }
    }

    public AxisEvaluator(String subQuery) {
        this.subQuery = subQuery;

    }

    @Override
    public XElements evaluate(Element element) {
        Elements filterScope = element.children();
        String axisString = subQuery;
        if(axisString.contains("/")){
            axisString = axisString.replace("/", "");
        }
        String axis = "";
        String tagName = "";
        if(axisString.contains("::")){
            String[] array = axisString.split("::");
            axis = array[0];
            tagName = array[1];
        }else if(axisString.contains(":")){
            String[] array = axisString.split(":");
            axis = array[0];
            tagName = array[1];
        }
        try {
            filterScope = getAxisScopeEls(axis, element);
            List<XElements> xElementses = new ArrayList<XElements>();

            for (Element chi : filterScope) {
                XElements fchi = filter(chi, tagName);
                if (fchi != null) {
                    xElementses.add(fchi);
                }
            }
            return new CombiningDefaultXElements(xElementses);
        } catch (NoSuchAxisException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean hasAttribute() {
        return false;
    }


    /**
     * 调用轴选择器
     *
     * @param axis
     * @param e
     * @return
     * @throws NoSuchAxisException
     */
    public Elements getAxisScopeEls(String axis, Element e) throws NoSuchAxisException {
        try {
            System.out.println(axis);
            String functionName = getJMethodNameFromStr(axis);
            System.out.println(functionName);
            System.out.println(axisFuncs.containsKey("followingSibling"));
            Method axisSelector = axisFuncs.get(renderFuncKey(functionName, e.getClass()));
            return (Elements) axisSelector.invoke(SingletonProducer.getInstance().getAxisSelector(), e);
        } catch (Exception e1) {
            throw new NoSuchAxisException("this axis is not supported, plase use other instead of '" + axis + "'");
        }
    }

    public static String getJMethodNameFromStr(String str){
        if (str.contains("-")){
            String[] pies = str.split("-");
            StringBuilder sb = new StringBuilder(pies[0]);
            for (int i=1;i<pies.length;i++){
                sb.append(pies[i].substring(0,1).toUpperCase()).append(pies[i].substring(1));
            }
            return sb.toString();
        }
        return str;
    }

    private static String renderFuncKey(String funcName, java.lang.Class... params) {
        return funcName + "|" + StringUtils.join(params, ",").replace("Document", "Element");
    }

    /**
     * 元素过滤器
     *
     * @param e
     * @param tagName
     * @return
     */
    public XElements filter(Element e, String tagName) throws NoSuchAxisException {
        Elements elements = new Elements();
        if (tagName.equals("*") || tagName.equals(e.nodeName())) {
            elements.add(e);
            return new DefaultXElements(elements, null);
        }
        return null;
    }

}
