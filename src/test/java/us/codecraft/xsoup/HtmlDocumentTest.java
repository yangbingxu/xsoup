package us.codecraft.xsoup;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import us.codecraft.xsoup.xevaluator.XPathParser;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * JXDocument Tester.
 *
 * @author <et.tw@163.com>
 * @version 1.0
 */
public class HtmlDocumentTest {
    String html;
    @Before
    public void before() throws Exception {
        html = "<html><head></head><body><div id=\"test\"class=\"test\">搜易贷致力于普惠金融，专注于互联网投资理财与小额贷款，搭建中国最大、用户体验最好的个人及中小企业的互联网信贷平台</div><div class=\"xiao\">Two</div><div class=\"ulss\"><ul><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li><li>10</li><li>11</li><li>12</li><li>13</li><li>14</li><li>15</li><li>16</li></ul></div></body></html>";
    }


    @Test
    public void testAxis() throws Exception {
        String xpath = "//div[@class='ulss']//ul/li[2]/following-sibling::*";
        Document document = Jsoup.parse(html);
        XPathEvaluator xPathEvaluator = XPathParser.parse(xpath);
        XElements elements = xPathEvaluator.evaluate(document);
        System.out.println("");
    }

    @Test
    public void testCombingXPath() {
        Document document = Jsoup.parse(html);
        XPathEvaluator xPathEvaluator = XPathParser.parse("//div[@id='test']/text() | //div[@id='test2']/text()");
        assertThat(xPathEvaluator.hasAttribute()).isTrue();
        XElements select = xPathEvaluator.evaluate(document);
        System.out.println(select.get());
    }

}
