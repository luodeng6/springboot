package org.deng.fileupload.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    String message;
    Integer states;
    boolean result;
    String method;

    public static Result access(String message,String method){
        return new Result(message,200,true,method);
    }
    public static Result defeat(String message,String method){
        return new Result(message,200,false,method);
    }
}
