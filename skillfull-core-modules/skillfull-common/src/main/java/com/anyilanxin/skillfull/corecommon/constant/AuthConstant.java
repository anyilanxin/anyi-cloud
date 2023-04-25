/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.corecommon.constant;

import com.anyilanxin.skillfull.corecommon.constant.impl.SysBaseType;

/**
* 系统基础配置常量
*
* @author zxiaozhou
* @date 2020-10-20 17:19
* @since JDK11
*/
public interface AuthConstant {
    /** 请求头token key键 */
    String BEARER_TOKEN_HEADER_NAME = "SkillfullAuthorization";

    /** query 参数token key键 */
    String ACCESS_TOKEN_QUERY_NAME = "skillfull_access_token";

    /** 超级管理员角色(系统最高权限) */
    String SUPER_ROLE = SysBaseType.SUPER_ROLE.getType();

    /** 按钮权限校验角色前缀 */
    String DEFAULT_ROLE_PREFIX = "ROLE_";

    /** 白名单或者不鉴权时默认权限表达式 */
    String PERMIT_ALL_EXPRESS = "permitAll()";

    /** 黑名单或者不允许访问时默认权限表达式 */
    String DENY_ALL_EXPRESS = "denyAll()";
}
