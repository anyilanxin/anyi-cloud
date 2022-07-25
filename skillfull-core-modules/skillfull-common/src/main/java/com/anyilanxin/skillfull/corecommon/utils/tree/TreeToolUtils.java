// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.utils.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.skillfull.corecommon.utils.tree.model.BaseTree;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author zxiaozhou
 */
public class TreeToolUtils<T extends BaseTree<T>> {
    /**
     * 默认子级键
     */
    private final static String DEFAULT_ID_KEY = "id";

    /**
     * 默认父级键
     */
    private final static String DEFAULT_PARENT_ID_KEY = "parentId";

    /**
     * 子父级数据判断
     */
    private final TreeId<T> getTreeId;

    /**
     * 根节点数据
     */
    private final List<T> rootList;

    /**
     * 其他节点数据(可包含父节点)
     */
    private final List<T> bodyList;


    /**
     * 获取树形对象(自定义子父级判断)
     *
     * @param rootList  ${@link  List<T>} 根节点数据
     * @param bodyList  ${@link  List<T>} 其余数据(可包含主节点)
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
     * @param rootList ${@link  List<T>} 根节点数据
     * @param bodyList ${@link  List<T>} 其余数据(可包含主节点)
     * @author zxiaozhou
     * @date 2020-08-26 18:08
     */
    public TreeToolUtils(@Nonnull List<T> rootList, List<T> bodyList) {
        this(rootList, bodyList, DEFAULT_ID_KEY, DEFAULT_PARENT_ID_KEY);
    }


    /**
     * 获取树形对象(自定义子父级id)
     *
     * @param rootList    ${@link  List<T>} 根节点数据
     * @param bodyList    ${@link  List<T>} 其余数据(可包含主节点数据)
     * @param idKey       ${@link String} 子级键
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
        bodyList.stream()
                .filter(c -> getTreeId.getId(beanTree).equals(getTreeId.getParentId(c)))
                .forEach(c -> {
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
