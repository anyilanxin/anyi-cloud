package indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct;

import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 实体键互转
 *
 * @author zxiaozhou
 * @date 2020-06-22 17:19
 * @since JDK11
 */
public interface BaseThreeMap<E, D, V> {
    //-------------E与D互转-------------

    /**
     * E转D
     *
     * @param e /
     * @return /
     */
    D eToD(E e);

    /**
     * E集合转D集合
     *
     * @param eList /
     * @return /
     */
    List<D> eToD(List<E> eList);


    /**
     * E更新D
     *
     * @param e /
     * @param d /
     * @return /
     */
    void updateEToD(E e, @MappingTarget D d);


    /**
     * E集合更新D集合
     *
     * @param eList /
     * @param dList /
     * @return /
     */
    void updateEListToDList(List<E> eList, @MappingTarget List<D> dList);

    /**
     * D转E
     *
     * @param d /
     * @return /
     */
    E dToE(D d);


    /**
     * D集合转E集合
     *
     * @param dList /
     * @return /
     */
    List<E> dToE(List<D> dList);

    /**
     * D更新E
     *
     * @param d /
     * @param e /
     */
    void updateDToE(D d, @MappingTarget E e);


    /**
     * D更新E
     *
     * @param dList /
     * @param eList /
     */
    void updateDListToEList(List<D> dList, @MappingTarget List<E> eList);


    //-------------E与V互转-------------

    /**
     * E转V
     *
     * @param e /
     * @return /
     */
    V eToV(E e);

    /**
     * E集合转V集合
     *
     * @param eList /
     * @return /
     */
    List<V> eToV(List<E> eList);


    /**
     * E更新V
     *
     * @param e /
     * @param v /
     */
    void updateEToV(E e, @MappingTarget V v);


    /**
     * E集合更新V集合
     *
     * @param eList /
     * @param vList /
     */
    void updateEListToVList(List<E> eList, @MappingTarget List<V> vList);


    /**
     * V转E
     *
     * @param v /
     * @return /
     */
    E vToE(V v);


    /**
     * V集合转E集合
     *
     * @param vList /
     * @return /
     */
    List<E> vToE(List<V> vList);

    /**
     * V更新E
     *
     * @param v /
     * @param e /
     */
    void updateVToE(V v, @MappingTarget E e);


    /**
     * V集合更新E集合
     *
     * @param vList /
     * @param eList /
     */
    void updateVListToEList(List<V> vList, @MappingTarget List<E> eList);


    //-------------D与V互转-------------

    /**
     * D转V
     *
     * @param d /
     * @return /
     */
    V dToV(D d);

    /**
     * D集合转V集合
     *
     * @param dList /
     * @return /
     */
    List<V> dToV(List<D> dList);


    /**
     * D更新V
     *
     * @param d /
     * @param v /
     */
    void updateDToV(D d, @MappingTarget V v);


    /**
     * D集合更新V集合
     *
     * @param dList /
     * @param vList /
     */
    void updateDListToVList(List<D> dList, @MappingTarget List<V> vList);


    /**
     * V转D
     *
     * @param v /
     * @return /
     */
    D vToD(V v);


    /**
     * V集合转D集合
     *
     * @param vList /
     * @return /
     */
    List<D> vToD(List<V> vList);

    /**
     * V更新D
     *
     * @param v /
     * @param d /
     */
    void updateVToD(V v, @MappingTarget D d);

    /**
     * V集合更新D集合
     *
     * @param vList /
     * @param dList /
     */
    void updateVListToDList(List<V> vList, @MappingTarget List<D> dList);
}
