package com.open.api.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 18:48
 */
@Data
public class AppIdModel {
    private String signType;
    /**
     * 验签公钥
     */
    private String publicKey;

    /**
     * ip1:port1,ip2:port2,ip3:port3
     */
    private Set<String> remoteInfo;

    /**
     * 负载均衡策略 random、weight、order，默认random
     */
    private String loadBalanceStrategy;

    /**
     * 权重集合。只有当策略是权重时才起作用，个数最好和配置机器节点数一致，否则 默认0
     * 1,3,2
     */
    private List<Integer> weight;
}
