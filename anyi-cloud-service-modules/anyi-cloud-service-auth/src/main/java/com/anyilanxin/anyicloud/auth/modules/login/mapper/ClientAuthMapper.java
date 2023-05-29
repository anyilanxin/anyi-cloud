

package com.anyilanxin.anyicloud.auth.modules.login.mapper;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.system.ClientAndResourceAuthModel;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

/**
 * 客户端授权mapper
 *
 * @author zhou
 * @date 2022-07-23 17:59
 * @since 1.0.0
 */
public interface ClientAuthMapper {

    /**
     * 获取客户端角色
     *
     * @param clientDetailId 客户端明细id
     * @return Set<ClientRoleModel> 查询结果
     * @author zxh
     * @date 2022-04-06 00:08
     */
    Set<RoleInfo> getClientAuthRole(@Param("clientDetailId") String clientDetailId);


    /**
     * 通过客户端id查询客户端信息
     *
     * @param clientId
     * @return ClientAndResourceAuthModel
     * @author zxh
     * @date 2022-07-12 12:18
     */
    ClientAndResourceAuthModel selectClientIdByClientId(@Param("clientId") String clientId);
}
