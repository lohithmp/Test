/*
 *
 * *
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *  Version:1.0
 *
 *
 *
 */

package com.epay.merchant.service;

import com.epay.merchant.dao.MerchantLoginDao;
import com.epay.merchant.dto.MerchantUserDto;
import com.epay.merchant.entity.MerchantUser;
import com.epay.merchant.exceptions.MerchantException;
import com.epay.merchant.model.request.AuthenticateUserRequest;
import com.epay.merchant.model.response.MerchantResponse;
import com.epay.merchant.util.ErrorConstants;
import com.epay.merchant.validatior.MerchantLoginValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.TokenRequest;
import com.sbi.epay.authentication.model.UserTokenRequest;
import com.sbi.epay.authentication.service.AuthenticationService;
import com.sbi.epay.authentication.util.enums.TokenType;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MerchantLoginService {

    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(MerchantLoginService.class);
    private final MerchantLoginDao merchantLoginDao;
    private final MerchantLoginValidation merchantLoginValidation;
    private final AuthenticationService authService;
    private final CaptchaService captchaService;
    private final ObjectMapper objectMapper;

    @Value("${token.expiry.time}")
    private int TOKEN_EXPIRY;

    public MerchantResponse<String> verifyUser(String username) {
        String loginType = String.valueOf(merchantLoginValidation.validate(username));
        log.info(" Request for Existing User with username {0} ", username);
        MerchantUserDto merchantDto = merchantLoginDao.findUserByUsername(username,loginType);
        return MerchantResponse.<String>builder().data(List.of(merchantDto.getUserName())).status(1).build();
    }

    public MerchantResponse<String> loginUser(String userName, AuthenticateUserRequest userRequest) {
        String loginType = String.valueOf(merchantLoginValidation.validate(userName));
        if (!captchaService.isCaptchaValid(userRequest.getRequestId(), userRequest.getCaptchaText())) {
            throw new MerchantException(ErrorConstants.ERROR_CAPTCHA_INVALID, MessageFormat.format(ErrorConstants.INVALID_CAPTCHA_ERROR_MESSAGE, ""));
        }
        MerchantUserDto merchantDto = merchantLoginDao.findUserByUsername(userName,loginType);
        MerchantUser merchantUser = objectMapper.convertValue(merchantDto,MerchantUser.class);

        if (merchantUser == null || ! passwordMatches(merchantUser, userRequest.getPassword())) {
            throw new MerchantException(ErrorConstants.ERROR_INVALID_CREDENTIALS, MessageFormat.format(ErrorConstants.INVALID_CREDENTIALS_ERROR_MESSAGE, ""));
        }
        String  token = authService.generateUserToken(buildAuthRequest(merchantDto));
        return MerchantResponse.<String>builder().data(List.of(token)).status(1).build();
    }


    private  UserTokenRequest buildAuthRequest(MerchantUserDto userInfo) {
        UserTokenRequest authRequest = new UserTokenRequest();
        authRequest.setTokenType(TokenType.USER);
        authRequest.setUsername(userInfo.getUserName());
        authRequest.setPassword(userInfo.getUserPassword());
        authRequest.setRoles(List.of(userInfo.getRole()));
        authRequest.setMid(userInfo.getMid());
        authRequest.setExpirationTime(TOKEN_EXPIRY);
        return authRequest;
    }

    private boolean passwordMatches(MerchantUser user, String password) {
        return user.getUserPassword().equals(password);
    }


}






@Component
@RequiredArgsConstructor
public class MerchantLoginDao {
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(MerchantLoginDao.class);
    private final MerchantLoginRepository merchantLoginRepository;
    private final ObjectMapper objectMapper;

    public MerchantUserDto findUserByUsername(String username,String loginType) {
        log.info(" request for Existing user from Merchant DB if present ");
         MerchantUser merchantUser = null;
        if (loginType.equalsIgnoreCase(String.valueOf(LoginType.MOBILE))){
          merchantUser = merchantLoginRepository.findByMobile(username).orElseThrow(() -> new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Active User "+username)));
        }else  if (loginType.equalsIgnoreCase(String.valueOf(LoginType.EMAIL))){
           merchantUser = merchantLoginRepository.findByEmail(username).orElseThrow(() -> new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Active User ")));
        }else  if (loginType.equalsIgnoreCase(String.valueOf(LoginType.USERID))){
            merchantUser = merchantLoginRepository.findByUserid(username).orElseThrow(() -> new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Active User "+username)));
        }else  if (loginType.equalsIgnoreCase(String.valueOf(LoginType.USERNAME))){
            merchantUser = merchantLoginRepository.findByUsername(username).orElseThrow(() -> new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Active User "+username)));
       }
        log.info(" getting Existing user from Merchant DB ");
        return objectMapper.convertValue(merchantUser, MerchantUserDto.class);
    }

}
