package com.open.api.config.sign;

import com.open.api.config.property.ApplicationProperty;
import com.open.api.enums.SignTypeEnum;
import com.open.api.model.AppIdModel;
import com.open.api.web.bo.GateWayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author rock
 * @detail RSA_AES
 * @date 2025/8/22 18:37
 */
@Slf4j
@Service
public class RsaAesOpenSignature extends OpenSignature<GateWayRequest> {
    @Autowired
    private ApplicationProperty applicationProperty;
    public RsaAesOpenSignature() {
        super(SignTypeEnum.RSA_AES);
    }

    @Override
    boolean verifySign(String appId,String publicKey, GateWayRequest request) {


        return false;
    }
}
