/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.corecommon.utils.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.skillfull.corecommon.utils.tree.model.BaseTree;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * @author zxiaozhou
 */
public class TreeToolUtils<T extends BaseTree<T>> {
    /** 默认子级键 */
    private static final String DEFAULT_ID_KEY = "id";

    /** 默认父级键 */
    private static final String DEFAULT_PARENT_ID_KEY = "parentId";

    /** 子父级数据判断 */
    private final TreeId<T> getTreeId;

    /** 根节点数据 */
    private final List<T> rootList;

    /** 其他节点数据(可包含父节点) */
    private final List<T> bodyList;

    /**
     * 获取树形对象(自定义子父级判断)
     *
     * @param rootList ${@link List<T>} 根节点数据
     * @param bodyList ${@link List<T>} 其余数据(可包含主节点)
     * @param getTreeId ${@link TreeId<T>} 子父级判断
     * @author zxiaozhou
     * @date 2020-08-26 18:07
     */
    public TreeToolUtils(@Nonnull List<T> rootList, List<T> bodyList, @Nonnull TreeId<T> getTreeId) {
        this.rootList = rootList;
        this.bodyList = bodyList;
        this.getTreeId = getTreeId;
    }


    /**
     * 获取树形对象(子方法父级id必须为parentId,子级id必须为id)
     *
     * @param rootList ${@link List<T>} 根节点数据
     * @param bodyList ${@link List<T>} 其余数据(可包含主节点)
     * @author zxiaozhou
     * @date 2020-08-26 18:08
     */
    public TreeToolUtils(@Nonnull List<T> rootList, List<T> bodyList) {
        this(rootList, bodyList, DEFAULT_ID_KEY, DEFAULT_PARENT_ID_KEY);
    }


    /**
     * 获取树形对象(自定义子父级id)
     *
     * @param rootList ${@link List<T>} 根节点数据
     * @param bodyList ${@link List<T>} 其余数据(可包含主节点数据)
     * @param idKey ${@link String} 子级键
     * @param parentIdKey ${@link String} 父级键
     * @author zxiaozhou
     * @date 2020-08-26 18:08
     */
    public TreeToolUtils(@Nonnull List<T> rootList, List<T> bodyList, @Nonnull String idKey, @Nonnull String parentIdKey) {
        this.rootList = rootList;
        this.bodyList = bodyList;
        this.getTreeId = new TreeId<T>() {
            @Override
            public String getId(T t) {
                String jsonString = JSONObject.toJSONString(t, SerializerFeature.WriteMapNullValue);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                if (jsonObject.containsKey(idKey)) {
                    return jsonObject.getString(idKey);
                }
                return "";
            }


            @Override
            public String getParentId(T t) {
                String jsonString = JSONObject.toJSONString(t, SerializerFeature.WriteMapNullValue);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                if (jsonObject.containsKey(parentIdKey)) {
                    return jsonObject.getString(parentIdKey);
                }
                return "";
            }
        };
    }


    /**
     * 创建树形数据
     *
     * @return List<T> ${@link List<T>} 树形数据结果
     * @author zxiaozhou
     * @date 2020-08-26 18:10
     */
    public List<T> getTree() {
        if (CollUtil.isNotEmpty(bodyList) && CollUtil.isNotEmpty(rootList)) {
            rootList.forEach(this::getChild);
            return rootList;
        } else if (CollUtil.isNotEmpty(bodyList) && CollectionUtil.isEmpty(rootList)) {
            bodyList.forEach(v -> v.setIsLeaf(true));
            return bodyList;
        } else if (CollUtil.isNotEmpty(rootList) && CollectionUtil.isEmpty(bodyList)) {
            rootList.forEach(v -> v.setIsLeaf(true));
            return rootList;
        } else {
            return Collections.emptyList();
        }
    }


    /**
     * 获取子节点
     *
     * @param beanTree ${@link T} 数据对象
     * @author zxiaozhou
     * @date 2020-08-26 18:10
     */
    private void getChild(T beanTree) {
        List<T> childList = Lists.newArrayList();
        bodyList.stream().filter(c -> getTreeId.getId(beanTree).equals(getTreeId.getParentId(c))).forEach(c -> {
            getChild(c);
            childList.add(c);
        });
        if (CollUtil.isNotEmpty(childList)) {
            beanTree.setHasChildren(true);
            beanTree.setChildren(childList);
        } else {
            beanTree.setIsLeaf(true);
        }
    }

    /**
     * 获取id、父级id
     *
     * @author zxiaozhou
     * @date 2020-07-21 13:12
     * @since JDK11
     */
    public interface TreeId<T> {
        /**
         * 获取id
         *
         * @param t ${@link T} 当前对象
         * @return String ${@link String} 返回获取id
         * @author zxiaozhou
         * @date 2020-07-21 13:21
         */
        String getId(T t);


        /**
         * 获取父级id
         *
         * @param t ${@link T} 当前对象
         * @return String ${@link String} 返回获取id
         * @author zxiaozhou
         * @date 2020-07-21 13:21
         */
        String getParentId(T t);
    }
}
