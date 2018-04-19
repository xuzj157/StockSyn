package personal.xuzj157.stocksyn.pojo.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BasicInfo {

    /**
     * 股票编号
     */
    @Id
    private String code;
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
    private String keyname;
    private String keycode;
    private String boardcode;
    private String boardname;

    /**
     * 所属行业
     */
    private String level2name;
    private String level2code;
}
