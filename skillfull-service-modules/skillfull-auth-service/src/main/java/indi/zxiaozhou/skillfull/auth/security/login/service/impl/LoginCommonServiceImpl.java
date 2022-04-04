// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.impl;

import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.OnlineUserPageVo;
import indi.zxiaozhou.skillfull.auth.security.login.service.ILoginCommonService;
import indi.zxiaozhou.skillfull.auth.security.login.service.dto.OnlineUserInfoDto;
import indi.zxiaozhou.skillfull.auth.security.login.service.mapstruct.UserInfoToOnlineUserInfoDtoMap;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户公共接口服务层实现
 *
 * @author zxiaozhou
 * @date 2021-06-03 23:58
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginCommonServiceImpl implements ILoginCommonService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserInfoToOnlineUserInfoDtoMap userInfoDtoMap;

    @Override
    public PageDto<OnlineUserInfoDto> selectOnlinePage(OnlineUserPageVo vo) {
//        PageDto<String> keysForPage = RedisUtils.findKeysForPage(SysBaseConstant.LOGIN_ONLINE_PREFIX, vo.getCurrent(), vo.getSize());
//        List<String> records = keysForPage.getRecords();
//        List<String> userIds = new ArrayList<>();
//        if (CollectionUtil.isEmpty(records)) {
//            records.forEach(v -> {
//                String[] split = v.split(":");
//                if (split.length == 3) {
//                    userIds.add(split[1]);
//                }
//            });
//            return new PageDto<>(0L, null);
//        }
//        List<Object> objectOnline = redisTemplate.opsForValue().multiGet(records);
//        List<Object> objectUserInfo = redisTemplate.opsForValue().multiGet(userIds);
//        List<LoginOnlineInfoModel> onlineInfo = JSONArray.parseArray(CoreCommonUtils.objectToJsonStr(objectOnline), LoginOnlineInfoModel.class);
//        List<LoginUserInfoModel> userInfo = JSONArray.parseArray(CoreCommonUtils.objectToJsonStr(objectUserInfo), LoginUserInfoModel.class);
//        List<OnlineUserInfoDto> collect = userInfo.stream().map(v -> {
//            OnlineUserInfoDto dto = userInfoDtoMap.aToB(v);
//            for (LoginOnlineInfoModel onlineInfoModel : onlineInfo) {
//                if (onlineInfoModel.getUserId().equals(v.getUserId())) {
//                    dto.setOnlineInfo(onlineInfoModel);
//                    break;
//                }
//            }
//            return dto;
//        }).collect(Collectors.toList());
//        return new PageDto<>(keysForPage.getTotal(), collect);
        return null;
    }


    @Override
    public void kickOut(String loginUnique) {
        // 直接设置缓存用户信息过期时间为2秒，触发消息中心发送客户端用户下线通知
        Boolean aBoolean = redisTemplate.hasKey(loginUnique);
        if (Objects.nonNull(aBoolean) && aBoolean) {
            redisTemplate.expire(loginUnique, 2, TimeUnit.SECONDS);
            String[] s = loginUnique.split("_");
            String systemId = s[s.length - 1];
//            redisTemplate.delete(SysBaseConstant.USER_TOKEN_SECURITY_PREFIX + systemId);
        }
    }
}
