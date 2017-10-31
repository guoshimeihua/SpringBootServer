package com.dodonew;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这里要自己配置扫描的路径，这点是要注意的。
 */
@SpringBootApplication
@MapperScan({"com.dodonew.dao"})
public class MyFirstSpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyFirstSpringbootApplication.class, args);
	}
}
