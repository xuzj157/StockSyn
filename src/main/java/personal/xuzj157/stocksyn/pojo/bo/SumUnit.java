package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;

@Data
public class SumUnit implements Comparable<SumUnit> {
    Double limit;
    /**
     * [n1]+[n2] < limit < [n3]+[n4]
     */
    Integer n1;   //涨
    Integer n2;   //跌
    Integer n3;   //涨
    Integer n4;   //跌

    public SumUnit() {
        this.n1 = 0;
        this.n2 = 0;
        this.n3 = 0;
        this.n4 = 0;
    }

    public SumUnit(Double limit) {
        this.limit = limit;
        this.n1 = 0;
        this.n2 = 0;
        this.n3 = 0;
        this.n4 = 0;
    }

    @Override
    public int compareTo(SumUnit o) {
        int sub1 = Math.abs(this.getN1() + this.getN4() - this.getN2() - this.getN3());
        int sub2 = Math.abs(o.getN1() + o.getN4() - o.getN2() - o.getN3());
        return sub1 > sub2 ? -1 : 1;
    }
}
