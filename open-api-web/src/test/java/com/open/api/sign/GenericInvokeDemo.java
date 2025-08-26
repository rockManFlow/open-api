package com.open.api.sign;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
/**
 * @author rock
 * @detail dubbo的泛化调用
 * @date 2025 /8/26 11:52
 */
public class GenericInvokeDemo {
    public static void main(String[] args) {
        // 1. 配置应用信息
        ApplicationConfig application = new ApplicationConfig();
        application.setName("generic-caller");

        // 2. 配置注册中心
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        // 3. 创建泛化引用配置
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface("com.example.DemoService"); // 服务接口全限定名
        reference.setGeneric(true); // 声明泛化调用

        // 4. 获取泛化服务代理
        GenericService genericService = reference.get();

        // 5. 发起泛化调用
        Object result = genericService.$invoke(
                "sayHello", // 方法名
                new String[]{"java.lang.String"}, // 参数类型数组
                new Object[]{"world"} // 参数值数组
        );
        System.out.println("调用结果: " + result);
    }
}
