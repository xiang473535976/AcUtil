package x.aichen.http.bean;

import java.io.Serializable;


public class Dto<T> implements Serializable {
    public  Integer code;//200：成功，500：失败
    public  String msg;
    public  T data;  //数据

}
