package personal.xuzj157.stocksyn.crawler.util.parse;

import org.apache.commons.cli.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import personal.xuzj157.stocksyn.crawler.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author larry
 * @version 创建时间 2013-6-14 下午3:56:48
 * @email larry.lv.word@gmail.com
 */
@SuppressWarnings("unused")
public class HtmlTool {
    public static final Options options = new Options();
    private static final Option option_select = new Option("select", "select", true, "根据 select 查找节点");
    private static final Option option_id = new Option("id", "id", true, "根据 id 查找节点");
    private static final Option option_tag = new Option("tag", "tag", true, "根据 tag 查找节点");
    private static final Option option_class = new Option("class", "class", true, "根据 class 查找节点");
    private static final Option option_next = new Option("next", "next", false, "根据 next 查找节点");
    private static final Option option_pre = new Option("pre", "pre", false, "根据 pre 查找节点");
    private static final Option option_nextsibling = new Option("nextsibling", "nextsibling", false, "根据 next 查找节点");
    private static final Option option_presibling = new Option("presibling", "presibling", false, "根据 pre 查找节点");
    private static final Option option_parent = new Option("parent", "parent", false, "根据 parent 查找节点");
    private static final Option option_last = new Option("last", "last", false, "根据 last 查找节点");
    private static final Option option_first = new Option("first", "first", false, "根据 first 查找节点");
    private static final Option option_index = new Option("index", "index", true, "根据 index 查找节点");
    private static final Option option_text = new Option("text", "text", false, "根据 text 查找值");
    private static final Option option_owntext = new Option("owntext", "owntext", false, "根据 text 查找值");
    private static final Option option_attr = new Option("attr", "attr", true, "根据 attr 查找值");
    private static final Option option_html = new Option("html", "html", false, "根据html 查找值");
//    private static Logger logger = Logger.getLogger(HtmlTool.class.getName());
    private static String ARGS_SEPARATOR = " ";
    private static CommandLineParser parser = new GnuParser();

    static {
        options.addOption(option_select);
        options.addOption(option_id);
        options.addOption(option_tag);
        options.addOption(option_class);
        options.addOption(option_nextsibling);
        options.addOption(option_presibling);

        options.addOption(option_next);
        options.addOption(option_pre);
        options.addOption(option_parent);
        options.addOption(option_last);
        options.addOption(option_first);

        options.addOption(option_index);
        options.addOption(option_text);
        options.addOption(option_attr);
        options.addOption(option_owntext);
        options.addOption(option_html);

    }

//    private String website = "";
//    private String crawl_token = "";
//    private ConcurrentHashMap<String, CopyOnWriteArrayList<String>> parameterMap;

//    public HtmlTool(String website, String crawl_token, HtmlPages htmlPages) {
//        this.website = website;
//        this.crawl_token = crawl_token;
//        this.parameterMap = htmlPages.getPages();
//    }

    // aql 使用说明:
    // aql 如果以 ; 分隔，表示管道，上一个操作的输出(必须为 Element )作为下一个操作的输入
    // 当使用-select -tag -class 查找节点时需要搭配 -index 使用，否则返回节点树
    // -id,--id <arg> 根据 id 查找节点
    // -select,--select <arg> 根据 select 查找节点
    // -tag,--tag <arg> 根据 tag 查找节点
    // -class,--class <arg> 根据 class 查找节点
    // -index,--index <arg> 根据 index 查找节点 没有此参数默认为 0
    // -text,--text 根据 text 查找值
    // -attr,--attr <arg> 根据 attr 查找值
    public static void main(String[] args) throws Exception {
//         HtmlTool analysishtml = new HtmlTool("");
//         Document doc = Tool_Parser.parser_html_bypath("doc/testonly.Alipay1.htm", "UTF-8");
//         String aql_alipay_user_name = "-id container;-select p.care-name.fn-left -index 0;-tag a -index 1;-text";
//         String analysis_value_safety = analysishtml.analysis_value_safety(doc, aql_alipay_user_name);
//         System.out.println(analysis_value_safety);
//         Elements analysis_elements_safety = analysishtml.elements(doc, "-id container;-select p.care-name.fn-left -index 0;-tag a");
//         System.out.println(analysis_elements_safety.toString());
    }

    // ///////////////////////////////////////////////////////////////公有方法/////////////////////////////////////////////////////////////////////////////

    /**
     * 获取安全方法链的 elements
     */
    public Elements elements(String attrbute, Element doc, String aql) {
    	try {
//        logger.debug("#获取 html 节点组 safety ".concat(aql));
        Elements elements = new Elements();
        String page = doc == null ? "" : doc.baseUri();
        try {
            Object tmp_result = analysis(doc, aql);
            if (tmp_result != null && tmp_result instanceof Elements) {
                elements = (Elements) tmp_result;
            }
        } catch (Exception e) {
            //Tool_Collect.collect(website, crawl_token, page, Level.WARN.name(), "解析 html 节点数组 ".concat(attrbute).concat("错误:").concat(aql), e);
//            logger.warn("解析 html 节点数组 ".concat(attrbute).concat("错误:").concat(aql), e);
        }
        return elements;
		
    	} catch (Exception e) {
    	return null;
		}
    }

    /** 获取安全方法链的值，方法中出现错误,或者返回值为空时返回传入的 value */
    /**
     * 根据jsoup 语句
     *
     * @param attrbute       记录log 日志中的提示信息
     * @param doc            要解析的文档
     * @param aql            解析文档的 jsoup 分析语句
     * @param default_result 如果根据 分析语句没有获得结果所返回的默认值
     * @return
     */
    public String value(String attrbute, Element doc, String aql, String default_result) {
//        logger.debug("#获取 html 节点 safety ".concat(aql));
        Object tmp_result = null;
        String page = doc == null ? "" : doc.baseUri();
        try {
            tmp_result = analysis(doc, aql);
            if (tmp_result == null) {
                //Tool_Collect.collect(website, crawl_token, page, Level.WARN.name(), "解析 html 节点 ".concat(attrbute).concat("为空:").concat(aql), null);
//                logger.warn("解析 html 节点 ".concat(attrbute).concat("为空:").concat(aql));
            }
        } catch (Exception e) {
            //Tool_Collect.collect(website, crawl_token, page, Level.WARN.name(), "解析 html 节点 ".concat(attrbute).concat("错误:").concat(aql), e);
//            logger.warn("解析 html 节点 ".concat(attrbute).concat("错误:").concat(aql), e);
        }
        default_result = (tmp_result == null || tmp_result.toString().isEmpty()) ? default_result : StringUtils.trim(tmp_result.toString()).replaceAll("\"", "");
        return default_result;
    }

    /**
     * 获取安全方法链的值，方法中出现错误,或者返回值为空时返回传入的 value
     */
    public String value_with_error(String attrbute, Element doc, String aql, String default_result) {
//        logger.debug("#获取 html 节点 safety ".concat(aql));
        Object tmp_result = null;
        String page = doc == null ? "" : doc.baseUri();
        try {
            tmp_result = analysis(doc, aql);
            if (tmp_result == null) {
                //Tool_Collect.collect(website, crawl_token, page, Level.WARN.name(), "解析 html 节点 ".concat(attrbute).concat("为空:").concat(aql), null);
//                logger.info("解析 html 节点 ".concat(attrbute).concat("为空:").concat(aql));
            }
        } catch (Exception e) {
            //Tool_Collect.collect(website, crawl_token, page, Level.ERROR.name(), "解析 html 节点 ".concat(attrbute).concat("错误:").concat(aql), e);
//            logger.warn("解析 html 节点 ".concat(attrbute).concat("错误:").concat(aql), e);
        }
        default_result = (tmp_result == null) ? default_result : StringUtils.trim(tmp_result.toString()).replaceAll("\"", "");
        return default_result;
    }

    // ///////////////////////////////////////////////////////////////私有方法//////////////////////////////////////////////////////////////////////

    /**
     * 解析 aql
     *
     * @param doc 要解析的文档
     * @param aql 用于解析的jsoup 语句
     * @return 解析结果
     * @throws Exception
     */
    private Object analysis(Element doc, String aql) throws Exception {
        Object result = doc;
        if (aql == null || aql.isEmpty()) {
            return null;
        }
        aql = StringUtils.trim(aql).substring(1);
        String[] split = aql.split(" -");
        for (String string : split) {
            String args_string = "-".concat(string);
            if (result instanceof Element) {
                result = by_element((Element) result, args_string);
            } else if (result instanceof Elements) {
                result = by_elements((Elements) result, args_string);
            } else {
                return null;
            }
        }
        return result;
    }

    /**
     * 根据 Element 获取值
     *
     * @param element
     * @param args_string
     * @return
     * @throws ParseException
     */
    private Object by_element(Element element, String args_string) throws ParseException {
        args_string = StringUtils.trim(args_string);
        CommandLine parse = parser.parse(options, args_string.split(ARGS_SEPARATOR));
        if (parse.hasOption(option_tag.getOpt())) {
            return element.getElementsByTag(parse.getOptionValue(option_tag.getOpt()));
        } else if (parse.hasOption(option_class.getOpt())) {
            return element.getElementsByClass(parse.getOptionValue(option_class.getOpt()));
        } else if (parse.hasOption(option_id.getOpt())) {
            return element.getElementById(parse.getOptionValue(option_id.getOpt()));
        } else if (parse.hasOption(option_select.getOpt())) {
            return element.select(parse.getOptionValue(option_select.getOpt()));
        } else if (parse.hasOption(option_parent.getOpt())) {
            return element.parent();
        } else if (parse.hasOption(option_attr.getOpt())) {
            return element.attr(parse.getOptionValue(option_attr.getOpt()));
        } else if (parse.hasOption(option_next.getOpt())) {
            return element.nextElementSibling();
        } else if (parse.hasOption(option_pre.getOpt())) {
            return element.previousElementSibling();
        } else if (parse.hasOption(option_text.getOpt())) {
            return element.text();
        } else if (parse.hasOption(option_owntext.getOpt())) {
            return element.ownText();
        } else if (parse.hasOption(option_nextsibling.getOpt())) {
            return element.nextSibling();
        } else if (parse.hasOption(option_presibling.getOpt())) {
            return element.previousSibling();
        } else if (parse.hasOption(option_html.getOpt())) {
            return element.html();
        } else {
//            logger.warn("#方法不能识别该参数:".concat(args_string));
            return null;
        }
    }

    /**
     * 根据 Elements 获取值
     *
     * @param elements
     * @param args_string
     * @return
     * @throws ParseException
     */
    private Object by_elements(Elements elements, String args_string) throws ParseException {
        args_string = StringUtils.trim(args_string);
        if (elements.isEmpty()) {
            return null;
        }
        CommandLine parse = parser.parse(options, args_string.split(ARGS_SEPARATOR));
        if (parse.hasOption(option_select.getOpt())) {
            return elements.select(parse.getOptionValue(option_select.getOpt()));
        } else if (parse.hasOption(option_first.getOpt())) {
            return elements.first();
        } else if (parse.hasOption(option_last.getOpt())) {
            return elements.last();
        } else if (parse.hasOption(option_index.getOpt())) {
            return elements.get(Integer.parseInt(parse.getOptionValue(option_index.getOpt())));
        } else if (parse.hasOption(option_text.getOpt())) {
            return elements.text();
        } else if (parse.hasOption(option_attr.getOpt())) {
            return elements.attr(parse.getOptionValue(option_attr.getOpt()));
        } else {
//            logger.warn("#方法不能识别该参数:".concat(args_string));
            return null;
        }
    }

    // /////////////////////////////映射 html 页面/////////////////////////////////////

    /**
     * 映射本地的 html 文件为 document
     */
    public Document html_bypath(String path_html, String encoding) throws IOException {
        File input = new File(path_html);
        Document doc = Jsoup.parse(input, encoding);
        return doc;
    }

    public Document html(String source) {
        source = source == null ? "" : source;
        Document doc = Jsoup.parse(source);
        return doc;
    }

    /**
     * 获取 表头中 文字所在列（如果不存在 将返回 999 存在返回 所在列的index 从0开始）
     */
    public int get_table_column_index(String searchName, Elements elements) {
        int index = 999;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).text().contains(searchName.trim())) {
                return i;
            }
        }
        return index;
    }
}