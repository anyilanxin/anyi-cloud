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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.corecommon.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ActionConstant;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 按钮权限检测类型
 *
 * @author zxiaozhou
 * @date 2020-09-11 12:12
 * @since JDK1.8
 */
@Getter
@ConstantType
public enum ActionType implements ISuperType {

  /** 拥有任意一个指定角色 */
  hasAnyRole(ActionConstant.HAS_ANY_ROLE, "拥有任意一个指定角色"),

  /** 拥有任意一个指定的权限 */
  hasAnyAuthority(ActionConstant.HAS_ANY_AUTHORITY, "拥有任意一个指定的权限"),

  /** 拥有任意一个指定Ip */
  hasAnyIpAddress(ActionConstant.HAS_ANY_IP_ADDRESS, "拥有任意一个指定Ip");

  /** 类型 */
  private final String type;

  /** 类型描述 */
  private final String typeDescribe;

  ActionType(String type, String typeDescribe) {
    this.type = type;
    this.typeDescribe = typeDescribe;
  }

  /**
   * 判断某个类型是否存在
   *
   * @param type ${@link String} 类型
   * @return boolean true-存在,false-不存在
   * @author zxiaozhou
   * @date 2020-09-11 16:02
   */
  public static boolean isHaveByType(String type) {
    ActionType[] values = ActionType.values();
    for (ActionType value : values) {
      if (value.type.equals(type)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断某个类型是否存在
   *
   * @param type ${@link String} 类型
   * @return boolean true-存在,false-不存在
   * @author zxiaozhou
   * @date 2020-09-11 16:02
   */
  public static ActionType getComplainByType(String type) {
    ActionType[] values = ActionType.values();
    for (ActionType value : values) {
      if (value.type.equals(type)) {
        return value;
      }
    }
    return null;
  }

  /**
   * 获取所有的类型
   *
   * @return String ${@link String}
   * @author zxiaozhou
   * @date 2020-09-11 16:45
   */
  public static String getAllType() {
    ActionType[] values = ActionType.values();
    StringBuilder sb = new StringBuilder();
    for (ActionType value : values) {
      sb.append("、").append(value.type);
    }
    return sb.toString().replaceFirst("、", "");
  }

  @Override
  public List<ConstantDictModel> getConstantDict() {
    List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
    ActionType[] values = ActionType.values();
    for (ActionType value : values) {
      ConstantDictModel dictDto = new ConstantDictModel();
      dictDto.setType(value.getType());
      dictDto.setTypeName(value.getTypeDescribe());
      dictDto.setTypeDescribe(value.getTypeDescribe());
      constantDictDtoList.add(dictDto);
    }
    return constantDictDtoList;
  }
}
