package com.czarea.rest.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhouzx
 */
@Data
@NoArgsConstructor
public class Foo {

    private Long id;
    private String name;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Foo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Foo{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", createTime=" + createTime +
            '}';
    }
}
