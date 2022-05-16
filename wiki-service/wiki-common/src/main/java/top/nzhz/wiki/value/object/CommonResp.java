package top.nzhz.wiki.value.object;

/**
 * @Class: CommonResp
 * @Description:
 * @Author: Xisha
 * @Date: 2022/05/16 21:35
 **/

public class CommonResp<T> {
    private boolean success = true;

    private int code;


    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T content;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommonResp{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
