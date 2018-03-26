package personal.xuzj157.stocksyn.enums;


public enum StatusCode {

    SUCCESS(20000,"invoke the api success"),
    TIME_OUT(30001,"the connect is timeout");

    private int code;
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
