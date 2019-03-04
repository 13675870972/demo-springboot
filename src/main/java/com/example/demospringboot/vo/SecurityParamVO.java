package com.example.demospringboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by haoxz11 on 2017/12/18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityParamVO<T> implements Serializable{
    private static final long serialVersionUID = -618995186694929070L;
    /**
     * 安全码
     */
    private String secureKey;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 参数
     */
    private T params;


    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public static <T> SecurityParamVO<T> of(String secureKey, String publicKey, T params) {
        SecurityParamVO<T> vo = new SecurityParamVO<T>();
        vo.setParams(params);
        vo.setPublicKey(publicKey);
        vo.setSecureKey(secureKey);
        return vo;
    }
}
