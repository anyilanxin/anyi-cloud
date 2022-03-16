// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.zxiaozhou.skillfull.auth.modules.common.service.IAuthCommonService;
import indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct.ConfigTokenMap;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacUserMapper;
import indi.zxiaozhou.skillfull.auth.security.config.properties.SecurityProperties;
import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.SmsCodeVo;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckDto;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckModel;
import indi.zxiaozhou.skillfull.auth.security.validate.IValidate;
import indi.zxiaozhou.skillfull.auth.security.validate.ValidateDto;
import indi.zxiaozhou.skillfull.auth.security.validate.impl.PictureValidate;
import indi.zxiaozhou.skillfull.auth.security.validate.impl.SmsValidate;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.ConfigTokenModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coremvc.utils.ContextHolderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.SYSTEM_BASE;

/**
 * 权限系统公共service接口实现
 *
 * @author zxiaozhou
 * @date 2020-11-02 10:27
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class AuthCommonServiceImpl implements IAuthCommonService {
    private final SecurityProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RbacUserMapper userMapper;
    private final ConfigTokenMap tokenMap;
    private IValidate pictureValidate;
    private IValidate smsValidate;

    @Autowired
    public void setPictureValidate(PictureValidate validate) {
        this.pictureValidate = validate;
    }

    @Autowired
    public void setSmsPictureValidate(SmsValidate validate) {
        this.smsValidate = validate;
    }

    @Override
    public void refreshSystemBaseInfo() throws RuntimeException {
        ConfigTokenModel systemBaseInfoModel = tokenMap.bToA(properties);
        redisTemplate.opsForValue().set(SYSTEM_BASE, systemBaseInfoModel);
    }


    @Override
    public void getNewPhoneSmsCode(String phone, HttpServletRequest request) throws RuntimeException {
        // 查看用户手机号码信息
        RbacUserEntity one = this.getUserByPhone(phone);
        if (Objects.nonNull(one)) {
            String userName = StringUtils.isNotBlank(one.getRealName()) ? one.getRealName() : (StringUtils.isNotBlank(one.getNickName()) ? one.getNickName() : one.getUserName());
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前手机号码已经管理用户:" + userName);
        }
        // 发送短信验证码
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(SmsValidate.PHONE, phone);
        smsValidate.getVerification(jsonObject, request);
    }

    /**
     * 验证用户手机信息是否存在
     *
     * @param phone ${@link String} 手机号码
     * @author zxiaozhou
     * @date 2021-01-13 10:50
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    RbacUserEntity getUserByPhone(String phone) {
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacUserEntity::getPhone, phone);
        return userMapper.selectOne(lambdaQueryWrapper);
    }


    @Override
    public void getOldPhoneSmsCode(String phone, HttpServletRequest request) throws RuntimeException {
        // 查看用户手机号码信息
        RbacUserEntity one = this.getUserByPhone(phone);
        if (Objects.isNull(one)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "当前手机号关联的用户信息不存在");
        }
        // 发送短信验证码
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(SmsValidate.PHONE, phone);
        smsValidate.getVerification(jsonObject, request);
    }


    @Override
    public void getOldPhoneSmsCodeAndAuth(String phone, HttpServletRequest request) throws RuntimeException {
        // 查看用户手机号码信息
        RbacUserEntity one = this.getUserByPhone(phone);
        if (Objects.isNull(one)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "当前手机号关联的用户信息不存在");
        }
        RbacUserEntity byId = userMapper.selectById(ContextHolderUtils.getUserId());
        if (StringUtils.isBlank(byId.getPhone())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前用户未绑定手机号码,请先绑定");
        }
        if (!byId.getPhone().equals(phone)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前手机号码与绑定手机号不一致");
        }
        // 发送短信验证码
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(SmsValidate.PHONE, phone);
        smsValidate.getVerification(jsonObject, request);
    }


    @Override
    public void getNewPhoneSmsCode(SmsCodeVo smsCodeVo, HttpServletRequest request) throws RuntimeException {
        CheckModel checkModel = new CheckModel(smsCodeVo.getCodeId(), smsCodeVo.getCodeValue());
        CheckDto checkDto = pictureValidate.checkVerification(checkModel);
        if (!checkDto.isResult()) {
            throw new ResponseException(checkDto.getMsg());
        }
        this.getNewPhoneSmsCode(smsCodeVo.getPhone(), request);
    }


    @Override
    public void getOldPhoneSmsCode(SmsCodeVo smsCodeVo, HttpServletRequest request) throws RuntimeException {
        CheckModel checkModel = new CheckModel(smsCodeVo.getCodeId(), smsCodeVo.getCodeValue());
        CheckDto checkDto = pictureValidate.checkVerification(checkModel);
        if (!checkDto.isResult()) {
            throw new ResponseException(checkDto.getMsg());
        }
        this.getOldPhoneSmsCode(smsCodeVo.getPhone(), request);
    }


    @Override
    public void getOldPhoneSmsCodeAndAuth(SmsCodeVo smsCodeVo, HttpServletRequest request) throws RuntimeException {
        CheckModel checkModel = new CheckModel(smsCodeVo.getCodeId(), smsCodeVo.getCodeValue());
        CheckDto checkDto = pictureValidate.checkVerification(checkModel);
        if (!checkDto.isResult()) {
            throw new ResponseException(checkDto.getMsg());
        }
        this.getOldPhoneSmsCodeAndAuth(smsCodeVo.getPhone(), request);
    }


    @Override
    public ValidateDto getPictureCode(HttpServletRequest request) throws RuntimeException {
        ValidateDto verification = pictureValidate.getVerification(null, request);
        if (!verification.isStatus()) {
            throw new ResponseException(verification.getMsg());
        }
        return verification;
    }
}
