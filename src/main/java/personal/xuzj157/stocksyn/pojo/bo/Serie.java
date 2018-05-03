package personal.xuzj157.stocksyn.pojo.bo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Serie implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;// 名字
    private List<Object> data;// 数据值

    public Serie() {}

    /**
     * @param name 名称（线条名称）
     * @param data 数据（线条上的所有数据值）
     */
    public Serie(String name, List<Object> data) {

        this.name = name;
        this.data = data;
    }

}

