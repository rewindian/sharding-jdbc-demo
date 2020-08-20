package io.ian.demo.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RestResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static int SUCCESS_STATUS = 0;
    public final static String SUCCESS_MESSAGE = "success";

    public final static int FAILED_STATUS = 1;
    public final static String FAILED_MESSAGE = "failed";

    private Integer status;

    private String desc;

    private Object result;

    public RestResult(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public RestResult(Integer status, String desc, Object result) {
        this.status = status;
        this.desc = desc;
        this.result = result;
    }

    public static RestResult getSuccessRestResult() {
        return new RestResult(SUCCESS_STATUS, SUCCESS_MESSAGE);
    }

    public static RestResult getSuccessRestResult(Object data) {
        return new RestResult(SUCCESS_STATUS, SUCCESS_MESSAGE, data);
    }

    public static RestResult getFailedRestResult() {
        return new RestResult(FAILED_STATUS, FAILED_MESSAGE);
    }
}
