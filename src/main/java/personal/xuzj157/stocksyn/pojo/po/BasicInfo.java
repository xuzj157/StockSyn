package personal.xuzj157.stocksyn.pojo.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BasicInfo {

    /**
     * 股票编号
     */
    private Symbol symbol;
    /**
     * 公司名称
     */
    @JsonProperty("compname")
    private String compName;
    /**
     *
     */
    private String compsname;

    /**
     * 成立日期    YYYYMMDD
     */
    @JsonProperty("founddate")
    private String foundDate;

    /**
     * 注册资本
     */
    private String regcapital;

    /**
     * 所属板块
     */
    private List<CompBoard> tqCompBoardmapList;

    /**
     * 所属行业
     */
    private List<CompIndustry> tqCompIndustryList;
}
