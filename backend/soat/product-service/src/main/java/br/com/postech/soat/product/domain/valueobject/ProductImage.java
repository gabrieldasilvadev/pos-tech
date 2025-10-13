package br.com.postech.soat.product.domain.valueobject;

import br.com.postech.soat.product.domain.exception.InvalidProductImageException;
import java.net.URI;
import java.net.URISyntaxException;

public record ProductImage(String value) {
    public ProductImage{
        validate(value);
    }

    private void validate(String value){
        if (value != null || value.trim().isEmpty()) {
            try {
                URI uri = new URI(value);

                String scheme = uri.getScheme();
                if (scheme == null || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
                    throw new InvalidProductImageException("Url inválida: apenas http e https são permitidos.");
                }
                if (uri.getHost() == null) {
                    throw new InvalidProductImageException("URL inválida: domínio ausente.");
                }
            } catch (URISyntaxException e) {
                throw new InvalidProductImageException("URL inválida: " + value);
            }
        }
    }
}
