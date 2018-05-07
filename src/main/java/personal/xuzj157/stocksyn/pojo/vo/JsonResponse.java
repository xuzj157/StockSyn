package personal.xuzj157.stocksyn.pojo.vo;

import jdk.net.SocketFlow;
import lombok.Data;
import personal.xuzj157.stocksyn.enums.StatusCode;

@Data
public class JsonResponse<T> {
    private T data;

    private int code;

    private String message;

    public void setStatusCode(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

    public void setResponse(StatusCode statusCode, T data) {
        setStatusCode(statusCode);
        this.data = data;
    }

    public JsonResponse(T data) {
        this.code = StatusCode.SUCCESS.getCode();
        this.message = StatusCode.SUCCESS.getMessage();
        this.data = data;
    }

    public JsonResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

}
