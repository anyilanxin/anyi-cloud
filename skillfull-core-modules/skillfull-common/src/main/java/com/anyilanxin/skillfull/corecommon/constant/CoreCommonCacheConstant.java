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

/**
* 缓存常量
*
* @author zxiaozhou
* @date 2020-07-22 16:18
* @since JDK11
*/
public interface CoreCommonCacheConstant {

    /** 字典信息缓存 */
    String ENGINE_DICT_CACHE = "ENGINE_DICT_CACHE";

    /** 常量字典缓存 */
    String ENGINE_CONSTANT_DICT_CACHE = "ENGINE_CONSTANT_DICT_CACHE:";

    /** 分类字典信息缓存 */
    String ENGINE_DICT_CATEGORY_CACHE = "ENGINE_DICT_CATEGORY_CACHE";

    /** 区域编码缓存 */
    String ENGINE_AREA_CACHE = "ENGINE_AREA_CACHE";

    /** 组织机构 */
    String ENGINE_ORG_CACHE = "ENGINE_ORG_CACHE";

    /** 有效路由缓存 */
    String SYSTEM_ROUTE_INFO_CACHE_PREFIX = "SYSTEM_ROUTE_INFO_CACHE:";

    /** 有效路由缓存锁 */
    String SYSTEM_ROUTE_INFO_CACHE_LOCK = "SYSTEM_ROUTE_INFO_CACHE_LOCK";

    /** 系统所有有效按钮权限缓存 */
    String SYSTEM_AUTH_ACTION_CACHE_PREFIX = "SYSTEM_AUTH_ACTION_CACHE:";

    /** 系统所有有效按钮权限缓存锁 */
    String SYSTEM_AUTH_ACTION_CACHE_LOCK = "SYSTEM_AUTH_ACTION_CACHE_LOCK";

    /** 数据加解密基本信息缓存 */
    String USER_DATA_SECURITY_CACHE = "USER_DATA_SECURITY_CACHE:";

    /** 数据加解密基本信息缓存待失效区 */
    String USER_DATA_SECURITY_WAIT_INVALID_CACHE = "USER_DATA_SECURITY_WAIT_INVALID_CACHE:";

    /** 权限相关前缀 */
    String AUTH_PREFIX = "SKILLFULL_AUTH:";
}
