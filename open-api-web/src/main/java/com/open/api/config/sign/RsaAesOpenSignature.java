package com.open.api.config.sign;

import com.alibaba.fastjson.JSON;
import com.open.api.enums.SignTypeEnum;
import com.open.api.util.AESCryptoUtils;
import com.open.api.util.RSACryptoUtils;
import com.open.api.web.bo.GateWayRequest;
import com.open.api.web.bo.SignInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author rock
 * @detail RSA_AES
 * @date 2025/8/22 18:37
 */
@Slf4j
@Service
public class RsaAesOpenSignature extends OpenSignature<GateWayRequest> {
    public RsaAesOpenSignature() {
        super(SignTypeEnum.RSA_AES);
    }

    @Override
    public boolean verifySign(String appId,String publicKey, GateWayRequest request) {
        log.info("RsaAesOpenSignature verifySign entry appId:{}",appId);
        try {
            //验签
            String s = RSACryptoUtils.decryptPublicKey(request.getSign(), publicKey);
            SignInfo signInfo = JSON.parseObject(s, SignInfo.class);
            if(signInfo==null){
                return false;
            }
            //解密
            String randomAesKey = signInfo.getRandomAesKey();
            String decryptContext = AESCryptoUtils.decrypt(request.getContent(), randomAesKey);
            request.setContent(decryptContext);
            request.setMethod(signInfo.getMethod());
            request.setRequestId(signInfo.getRequestId());
            request.setRandomAesKey(randomAesKey);
            request.setUri(signInfo.getUri());
            log.info("RsaAesOpenSignature verifySign decrypt success appId:{}",appId);
            return true;
        }catch (Exception e){
            log.error("RsaAesOpenSignature error appId:{}",appId,e);
        }
        return false;
    }

    @Override
    public String encryptResult(String context, String secretKey) throws Exception {
        return AESCryptoUtils.encrypt(context, secretKey);
    }
}
