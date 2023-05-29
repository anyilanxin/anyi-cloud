

package com.anyilanxin.anyicloud.corecommon.base.service.mapstruct;

import java.util.List;
import java.util.Set;

import org.mapstruct.MappingTarget;

/**
 * Dto与Entity相互转换基类
 *
 * @author zxh
 * @date 2020-06-22 17:19
 * @since 1.0.0
 */
public interface BaseMap<A, B> {
    /**
     * A转B
     *
     * @param a /
     * @return /
     */
    B aToB(A a);


    /**
     * A集合转B集合
     *
     * @param aList /
     * @return /
     */
    List<B> aToB(List<A> aList);


    /**
     * A集合转B集合
     *
     * @param aSet /
     * @return /
     */
    Set<B> aToB(Set<A> aSet);


    /**
     * A更新B
     *
     * @param a /
     * @param b /
     * @return /
     */
    void updateAToB(A a, @MappingTarget B b);


    /**
     * A集合更新B集合
     *
     * @param aList /
     * @param bList /
     * @return /
     */
    void updateAListToBList(List<A> aList, @MappingTarget List<B> bList);


    /**
     * B转A
     *
     * @param b /
     * @return /
     */
    A bToA(B b);


    /**
     * B集合转A集合
     *
     * @param bList /
     * @return /
     */
    List<A> bToA(List<B> bList);


    /**
     * B集合转A集合
     *
     * @param bSet /
     * @return /
     */
    Set<A> bToA(Set<B> bSet);


    /**
     * B更新A
     *
     * @param b /
     * @param a /
     */
    void updateBToA(B b, @MappingTarget A a);


    /**
     * B集合更新A集合
     *
     * @param bList /
     * @param aList /
     */
    void updateBListToAList(List<B> bList, @MappingTarget List<A> aList);
}
