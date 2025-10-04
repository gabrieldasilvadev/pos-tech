package br.com.postech.soat.commons.infrastructure.security;

import br.com.postech.soat.commons.infrastructure.security.http.JwksController;
import com.nimbusds.jose.jwk.RSAKey;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JwksControllerTest {

    private MockMvc mockMvc;
    private RsaKeyProvider keyProvider;

    @Test
    void shouldExposeJwksJson() throws Exception {
        keyProvider = mock(RsaKeyProvider.class);
        JwksController controller = new JwksController(keyProvider);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair keyPair = gen.generateKeyPair();

        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
            .keyID(UUID.randomUUID().toString())
            .algorithm(com.nimbusds.jose.JWSAlgorithm.RS256)
            .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
            .build();

        when(keyProvider.getRsaJwk()).thenReturn(rsaKey);

        mockMvc.perform(get("/.well-known/jwks.json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.keys[0].kty").value("RSA"))
            .andExpect(jsonPath("$.keys[0].use").value("sig"))
            .andExpect(jsonPath("$.keys[0].alg").value("RS256"))
            .andExpect(jsonPath("$.keys[0].kid").exists())
            .andExpect(jsonPath("$.keys[0].n").exists())
            .andExpect(jsonPath("$.keys[0].e").exists());
    }

    @Test
    void shouldExposeMultipleKeysWhenAvailable() throws Exception {
        keyProvider = mock(RsaKeyProvider.class);
        JwksController controller = new JwksController(keyProvider);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair keyPair1 = gen.generateKeyPair();
        KeyPair keyPair2 = gen.generateKeyPair();

        RSAKey rsaKey1 = new RSAKey.Builder((RSAPublicKey) keyPair1.getPublic())
            .keyID("key-1")
            .algorithm(com.nimbusds.jose.JWSAlgorithm.RS256)
            .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
            .build();

        RSAKey rsaKey2 = new RSAKey.Builder((RSAPublicKey) keyPair2.getPublic())
            .keyID("key-2")
            .algorithm(com.nimbusds.jose.JWSAlgorithm.RS256)
            .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
            .build();

        when(keyProvider.getRsaJwk()).thenReturn(rsaKey1).thenReturn(rsaKey2);

        mockMvc.perform(get("/.well-known/jwks.json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.keys[0].kid").value("key-1"));

        mockMvc.perform(get("/.well-known/jwks.json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.keys[0].kid").value("key-2"));
    }

    @Test
    void shouldReturnJsonContentType() throws Exception {
        keyProvider = mock(RsaKeyProvider.class);
        JwksController controller = new JwksController(keyProvider);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair keyPair = gen.generateKeyPair();

        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
            .keyID(UUID.randomUUID().toString())
            .algorithm(com.nimbusds.jose.JWSAlgorithm.RS256)
            .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
            .build();

        when(keyProvider.getRsaJwk()).thenReturn(rsaKey);

        mockMvc.perform(get("/.well-known/jwks.json"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    void shouldReturnEmptyKeysWhenNoKeyAvailable() throws Exception {
        keyProvider = mock(RsaKeyProvider.class);
        JwksController controller = new JwksController(keyProvider);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(keyProvider.getRsaJwk()).thenReturn(null);

        mockMvc.perform(get("/.well-known/jwks.json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.keys").isEmpty());
    }
}
