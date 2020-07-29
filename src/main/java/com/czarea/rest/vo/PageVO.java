package com.czarea.rest.vo;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author zhouzx
 */
@Data
public class PageVO<E> {

    @NotNull
    @Range(min = 1, max = 300)
    private Integer size;

    @NotNull
    @Range(min = 0)
    private Integer current;

    private String kw;

    private String[] ascs;

    private String[] descs;


}
