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

///*
// * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
// *
// * 本软件 AnYi Cloud EE 为 AnYi Cloud 的商业授权软件。未经过商业授权禁止使用，违者必究。
// *
// * AnYi Cloud EE 为商业授权软件，您在使用过程中，需要注意以下几点：
// *   1.不允许在国家法律法规规定的范围外使用，如出现违法行为作者本人不承担任何责任；
// *   2.软件使用的第三方依赖皆为开源软件，如需要修改第三方依赖请遵循第三方依赖附带的开源协议，因擅自修改第三方依赖所引起的争议，作者不承担任何责任；
// *   3.不得基于AnYi Cloud EE的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
// *   4.不得将软件源码以任何开源方式公布出去；
// *   5.不得对授权进行出租、出售、抵押或发放子许可证；
// *   6.您可以直接使用在自己的网站或软件产品中，也可以集成到您自己的商业网站或软件产品中进行出租或销售；
// *   7.您可以对上述授权软件进行必要的修改和美化，无需公开修改或美化后的源代码；
// *   8.本软件流程部分请遵循camunda开源协议：
// *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
// *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
// *   9.除满足上面条款外，在其他商业领域使用不受影响。同时作者为商业授权使用者在使用过程中出现的纠纷提供协助。
// */
//package com.anyilanxin.anyicloud.gateway.utils;
//
//import com.anyilanxin.anyicloud.oauth2webflux.config.access.ConfigAttribute;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Stores a {@link ConfigAttribute} as a <code>String</code>.
// *
// * @author Ben Alex
// */
//public class SecurityConfig implements ConfigAttribute {
//
//    private final String attrib;
//
//    public SecurityConfig(String config) {
//        Assert.hasText(config, "You must provide a configuration attribute");
//        this.attrib = config;
//    }
//
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof ConfigAttribute) {
//            ConfigAttribute attr = (ConfigAttribute) obj;
//            return this.attrib.equals(attr.getAttribute());
//        }
//        return false;
//    }
//
//
//    @Override
//    public String getAttribute() {
//        return this.attrib;
//    }
//
//
//    @Override
//    public int hashCode() {
//        return this.attrib.hashCode();
//    }
//
//
//    @Override
//    public String toString() {
//        return this.attrib;
//    }
//
//
//    public static List<ConfigAttribute> createListFromCommaDelimitedString(String access) {
//        return createList(StringUtils.commaDelimitedListToStringArray(access));
//    }
//
//
//    public static List<ConfigAttribute> createList(String... attributeNames) {
//        Assert.notNull(attributeNames, "You must supply an array of attribute names");
//        List<ConfigAttribute> attributes = new ArrayList<>(attributeNames.length);
//        for (String attribute : attributeNames) {
//            attributes.add(new SecurityConfig(attribute.trim()));
//        }
//        return attributes;
//    }
//
//}
