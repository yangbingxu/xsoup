package us.codecraft.xsoup.xevaluator;

/**
 * Created by Admin on 2017/12/31.
 */

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 通过轴选出对应作用域的全部节点
 * 去掉不实用的轴，不支持namespace，attribute（可用 /@*代替），preceding(preceding-sibling支持)，following(following-sibling支持)
 * 添加 preceding-sibling-one，following-sibling-one,即只选前一个或后一个兄弟节点，添加 sibling 选取全部兄弟节点
 * @author: github.com/zhegexiaohuozi [seimimaster@gmail.com]
 * Date: 14-3-15
 */
//轴
//self 节点自身
//parent 父节点
//child 直接子节点
//ancestor 全部祖先节点 父亲，爷爷 ， 爷爷的父亲...
//ancestor-or-self全部祖先节点和自身节点
//descendant 全部子代节点 儿子，孙子，孙子的儿子...
//descendant-or-self 全部子代节点和自身
//preceding-sibling 节点前面的全部同胞节点
//following-sibling 节点后面的全部同胞节点
//轴实用扩展
//preceding-sibling-one 前一个同胞节点（扩展）
//following-sibling-one 返回下一个同胞节点(扩展) 语法
//sibling 全部同胞（扩展）
public class AxisSelector {
    /**
     * 自身
     * @param e
     * @return
     */
    public Elements self(Element e){
        return new Elements(e);
    }

    /**
     * 父节点
     * @param e
     * @return
     */
    public Elements parent(Element e){
        return new Elements(e.parent());
    }

    /**
     * 直接子节点
     * @param e
     * @return
     */
    public Elements child(Element e){
        return e.children();
    }

    /**
     * 全部祖先节点 父亲，爷爷 ， 爷爷的父亲...
     * @param e
     * @return
     */
    public Elements ancestor(Element e){
        return e.parents();
    }

    /**
     * 全部祖先节点和自身节点
     * @param e
     * @return
     */
    public Elements ancestorOrSelf(Element e){
        Elements rs=e.parents();
        rs.add(e);
        return rs;
    }

    /**
     * 全部子代节点 儿子，孙子，孙子的儿子...
     * @param e
     * @return
     */
    public Elements descendant(Element e){
        return e.getAllElements();
    }

    /**
     * 全部子代节点和自身
     * @param e
     * @return
     */
    public Elements descendantOrSelf(Element e){
        Elements rs = e.getAllElements();
        rs.add(e);
        return rs;
    }

    /**
     * 节点前面的全部同胞节点，preceding-sibling
     * @param e
     * @return
     */
    public Elements precedingSibling(Element e){
        Elements rs = new Elements();
        Element tmp = e.previousElementSibling();
        while (tmp!=null){
            rs.add(tmp);
            tmp = tmp.previousElementSibling();
        }
        return rs;
    }

    /**
     * 返回前一个同胞节点（扩展），语法 preceding-sibling-one
     * @param e
     * @return
     */
    public Elements precedingSiblingOne(Element e){
        Elements rs = new Elements();
        if (e.previousElementSibling()!=null){
            rs.add(e.previousElementSibling());
        }
        return rs;
    }

    /**
     * 节点后面的全部同胞节点following-sibling
     * @param e
     * @return
     */
    public Elements followingSibling(Element e){
        Elements rs = new Elements();
        Element tmp = e.nextElementSibling();
        while (tmp!=null){
            rs.add(tmp);
            tmp = tmp.nextElementSibling();
        }
        return rs;
    }

    /**
     * 返回下一个同胞节点(扩展) 语法 following-sibling-one
     * @param e
     * @return
     */
    public Elements followingSiblingOne(Element e){
        Elements rs = new Elements();
        if (e.nextElementSibling()!=null){
            rs.add(e.nextElementSibling());
        }
        return rs;
    }

    /**
     * 全部同胞（扩展）
     * @param e
     * @return
     */
    public Elements sibling(Element e){
        return e.siblingElements();
    }

}