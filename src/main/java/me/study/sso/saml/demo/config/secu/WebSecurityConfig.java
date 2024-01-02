/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : HyungSeok Kim
 * Create Date : 2023. 12. 26.
 * File Name : WebSecurityConfig.java
 * DESC :
 *****************************************************************/
package me.study.sso.saml.demo.config.secu;

import org.opensaml.security.x509.X509Support;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.RelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;

@ConditionalOnClass(name = {"org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport"})
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, RelyingPartyRegistrationRepository relyingPartyRegistrationRepository) throws Exception {
        final RelyingPartyRegistrationResolver relyingPartyRegistrationResolver = new DefaultRelyingPartyRegistrationResolver(relyingPartyRegistrationRepository);
        final Saml2MetadataFilter metadataFilter = new Saml2MetadataFilter(relyingPartyRegistrationResolver, new OpenSamlMetadataResolver());
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/error/**", "/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .saml2Login(Customizer.withDefaults())
                .saml2Logout(Customizer.withDefaults())
                .saml2Metadata(Customizer.withDefaults());
//                .addFilterBefore(metadataFilter, Saml2MetadataFilter.class);

        return http.build();
    }

    private RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() throws IOException, CertificateException {
        final File verificationKey = new ClassPathResource("").getFile();
        final X509Certificate certificate = Objects.requireNonNull(X509Support.decodeCertificate(verificationKey), "X509 certificate cannot be null");
        final Saml2X509Credential credential = Saml2X509Credential.verification(certificate);
        final RelyingPartyRegistration registration = RelyingPartyRegistration
                .withRegistrationId("okta-saml")
                .assertingPartyDetails(party -> party
                        .entityId("http://www.okta.com/exke4qnphubxOqwh25d7")
                        .singleSignOnServiceLocation("")
                        .wantAuthnRequestsSigned(false)
                        .verificationX509Credentials(saml2X509Credentials -> saml2X509Credentials.add(credential)))
                .build();

        return new InMemoryRelyingPartyRegistrationRepository(registration);
    }
}
