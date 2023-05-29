

package com.anyilanxin.anyicloud.corecommon.utils.tree.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 树形基础类
 *
 * @author zxh
 * @date 2020-07-10 11:48
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class BaseTree<T> implements Serializable {
    private static final long serialVersionUID = 1354907530884949491L;

    @Schema(name = "children", title = "子节点信息")
    private List<T> children;

    @Schema(name = "isLeaf", title = "叶子节点,默认false")
    private boolean isLeaf;

    @Schema(name = "hasChildren", title = "有子节点,默认false")
    private boolean hasChildren;

    public boolean getIsLeaf() {
        return this.isLeaf;
    }


    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
