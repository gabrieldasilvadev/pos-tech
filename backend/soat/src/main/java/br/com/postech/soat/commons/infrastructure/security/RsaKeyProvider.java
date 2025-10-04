package br.com.postech.soat.commons.infrastructure.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Component;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Component
public class RsaKeyProvider {

    private final RSAKey rsaJwk;

    public RsaKeyProvider() {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            KeyPair kp = gen.generateKeyPair();

            this.rsaJwk = new RSAKey.Builder((RSAPublicKey) kp.getPublic())
                .privateKey((RSAPrivateKey) kp.getPrivate())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(UUID.randomUUID().toString())
                .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate RSA key pair for JWT", e);
        }
    }

    public RSAPrivateKey getPrivateKey() throws JOSEException {
        return (RSAPrivateKey) rsaJwk.toPrivateKey();
    }

    public RSAPublicKey getPublicKey() throws JOSEException {
        return (RSAPublicKey) rsaJwk.toPublicKey();
    }
    public String getKeyId() {
        return rsaJwk.getKeyID();
    }

    public String getJwksJson() {
        JWKSet jwkSet = new JWKSet(rsaJwk.toPublicJWK());
        return jwkSet.toString();
    }

    public RSAKey getRsaJwk() {
        return rsaJwk;
    }
}
