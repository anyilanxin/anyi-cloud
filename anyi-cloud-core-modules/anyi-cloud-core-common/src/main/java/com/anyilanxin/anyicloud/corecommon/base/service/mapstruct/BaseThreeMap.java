/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.base.service.mapstruct;

import java.util.List;
import java.util.Set;
import org.mapstruct.MappingTarget;

/**
 * 实体键互转
 *
 * @author zxh
 * @date 2020-06-22 17:19
 * @since 1.0.0
 */
public interface BaseThreeMap<E, D, V> {
    // -------------E与D互转-------------

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
     * E集合转D集合
     *
     * @param eSet /
     * @return /
     */
    Set<D> eToD(Set<E> eSet);


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
     * D集合转E集合
     *
     * @param dSet /
     * @return /
     */
    Set<E> dToE(Set<D> dSet);


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

    // -------------E与V互转-------------


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
     * E集合转V集合
     *
     * @param eSet /
     * @return /
     */
    Set<V> eToV(Set<E> eSet);


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
     * V集合转E集合
     *
     * @param vSet /
     * @return /
     */
    Set<E> vToE(Set<V> vSet);


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

    // -------------D与V互转-------------


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
     * D集合转V集合
     *
     * @param dSet /
     * @return /
     */
    Set<V> dToV(Set<D> dSet);


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
     * V集合转D集合
     *
     * @param vSet /
     * @return /
     */
    Set<D> vToD(Set<V> vSet);


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
