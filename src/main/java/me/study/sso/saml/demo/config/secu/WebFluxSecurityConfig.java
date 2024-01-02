/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : HyungSeok Kim
 * Create Date : 2023. 12. 26.
 * File Name : WebfluxSecurityConfig.java
 * DESC :
 *****************************************************************/
package me.study.sso.saml.demo.config.secu;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@ConditionalOnClass(name = {"org.springframework.web.reactive.config.WebFluxConfigurer"})
@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {
}
