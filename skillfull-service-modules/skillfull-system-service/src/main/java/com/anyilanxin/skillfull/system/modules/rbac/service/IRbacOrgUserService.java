package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacJoinOrgVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgUserEntity;

/**
 * 机构-用户(RbacOrgUser)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
public interface IRbacOrgUserService extends BaseService<RbacOrgUserEntity> {
    /**
     * 加入机构
     *
     * @param vo
     * @author zxiaozhou
     * @date 2022-07-11 00:42
     */
    void joinOrg(RbacJoinOrgVo vo);


    /**
     * 移除机构
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @author zxiaozhou
     * @date 2022-07-11 00:43
     */
    void removeOrg(String userId, String orgId);
}
