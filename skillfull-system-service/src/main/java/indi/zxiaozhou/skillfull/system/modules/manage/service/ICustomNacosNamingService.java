// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service;

import com.alibaba.nacos.api.naming.pojo.Instance;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.InstancePageVo;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.NacosNamespacesDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.NacosServiceInfoDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.ServiceInstancePageDto;

import java.util.List;

/**
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 19:34
 * @since JDK1.8
 */
public interface ICustomNacosNamingService {


    /**
     * 查询所有注册的服务实例信息
     *
     * @param serviceCode ${@link String} 服务编码(必填)
     * @param groupName   ${@link String} 组，不填使用默认
     * @param clusters    ${@link List<String>} 集群信息，不变默认
     * @return List<Instance> ${@link List<Instance>} 服务实例
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 21:51
     */
    List<Instance> getAllInstances(String serviceCode, String groupName, List<String> clusters) throws RuntimeException;


    /**
     * 获取当前所有命名空间
     *
     * @return List<NacosNamespacesDto> ${@link List<NacosNamespacesDto>} 命名空间
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 21:52
     */
    List<NacosNamespacesDto> getAllNamespaces() throws RuntimeException;

    /**
     * 服务实例分页查询
     *
     * @param vo ${@link InstancePageVo}
     * @return PageDto<ServiceInstancePageDto> ${@link PageDto < ServiceInstancePageDto >}
     * @author zxiaozhou
     * @date 2021-12-28 05:54
     */
    PageDto<ServiceInstancePageDto> selectInstancePage(InstancePageVo vo);

    /**
     * 查询某个组的所有服务
     *
     * @param pageNo    ${@link Integer} 查询页(不填默认1)
     * @param pageSize  ${@link Integer} 显示数量(不变默认最大)
     * @param groupName ${@link String} 组名
     * @return List<NacosServiceInfoDto> ${@link  List<NacosServiceInfoDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 21:54
     */
    List<NacosServiceInfoDto> getServicesOfServer(Integer pageNo, Integer pageSize, String groupName) throws RuntimeException;

}
