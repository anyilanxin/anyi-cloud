/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.base.service.mapstruct;

import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

/**
 * Dto与Entity相互转换基类
 *
 * @author zxh
 * @date 2020-06-22 17:19
 * @since 1.0.0
 */
public interface AnYiBaseMap<A, B> {
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
