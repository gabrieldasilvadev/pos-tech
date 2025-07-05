package br.com.postech.soat.customer.domain.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaskUtilTest {
    @Test
    void givenCpf_whenMaskCpf_thenReturnMaskedCpf() {
        assertEquals("12*****90", MaskUtil.maskCpf("123456790"));
        assertEquals("12*****01", MaskUtil.maskCpf("12345678901"));
        assertNull(MaskUtil.maskCpf(null));
        assertEquals("123", MaskUtil.maskCpf("123"));
    }

    @Test
    void givenEmail_whenMaskEmail_thenReturnMaskedEmail() {
        assertEquals("jo*******@mail.com", MaskUtil.maskEmail("joaozinho@mail.com"));
        assertEquals("**@mail.com", MaskUtil.maskEmail("jo@mail.com"));
        assertEquals("@mail.com", MaskUtil.maskEmail("@mail.com"));
        assertNull(MaskUtil.maskEmail(null));
        assertEquals("invalid", MaskUtil.maskEmail("invalid"));
    }

    @Test
    void givenPhone_whenMaskPhone_thenReturnMaskedPhone() {
        assertEquals("11******89", MaskUtil.maskPhone("1198765489"));
        assertEquals("119*****789", MaskUtil.maskPhone("119876543789"));
        assertNull(MaskUtil.maskPhone(null));
    }
}

