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

package com.anyilanxin.skillfull.system.modules.manage.service;

import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosAllInstancesQueryVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosGroupNameVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosSubscribeVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosUpdateInstanceVo;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.NacosServiceInfoDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ServiceInstanceDto;
import java.util.List;

/**
 * nacos open api接口二次封装
 *
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 10:50
 * @since JDK1.8
 */
public interface INacosService {

  /**
   * 订阅服务变化监听
   *
   * @param vo ${@link NacosSubscribeVo} 查询条件
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou zxiaozhou
   * @date 2020-10-11 13:03
   */
  void subscribe(NacosSubscribeVo vo) throws RuntimeException;

  /**
   * 取消服务变化订阅
   *
   * @param vo ${@link NacosSubscribeVo} 查询条件
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou zxiaozhou
   * @date 2020-10-11 13:03
   */
  void unsubscribe(NacosSubscribeVo vo) throws RuntimeException;

  /**
   * 服务实例上下线
   *
   * @param vo ${@link NacosUpdateInstanceVo} 查询条件
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2020-10-11 13:43
   */
  void updateInstance(NacosUpdateInstanceVo vo) throws RuntimeException;

  /**
   * 查询某个服务所有实例
   *
   * @param vo ${@link NacosAllInstancesQueryVo} 查询条件
   * @return ServiceInstanceDto ${@link ServiceInstanceDto} 实例
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou zxiaozhou
   * @date 2020-10-11 13:03
   */
  ServiceInstanceDto getAllInstances(NacosAllInstancesQueryVo vo) throws RuntimeException;

  /**
   * 获取已经注册的服务
   *
   * @param vo ${@link NacosGroupNameVo} 查询条件
   * @return List<String> ${@link List<String>}
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou zxiaozhou
   * @date 2020-10-11 13:21
   */
  List<NacosServiceInfoDto> getServices(NacosGroupNameVo vo) throws RuntimeException;
}
