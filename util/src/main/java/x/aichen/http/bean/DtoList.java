package x.aichen.http.bean;

import java.io.Serializable;
import java.util.List;


public class DtoList<T> implements Serializable {
    public Integer code;//200：成功，500：失败
    public String msg;
    public List<T> data;  //数据
}
