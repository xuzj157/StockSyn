package personal.xuzj157.stocksyn.pojo.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "basic_info")
@Data
public class BasicInfo {

    /**
     * 股票编号
     */
    @DBRef
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

    @Data
    public class CompBoard {
        private String keyname;
        private String keycode;
        private String boardcode;
        private String boardname;
    }

    @Data
    public class CompIndustry {
        private String level2name;
        private String level2code;
    }
}
