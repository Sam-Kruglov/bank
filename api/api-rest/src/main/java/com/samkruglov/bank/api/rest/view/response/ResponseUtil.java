package com.samkruglov.bank.api.rest.view.response;

import com.samkruglov.bank.domain.common.Identifiable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

public final class ResponseUtil {

    private ResponseUtil() {
    }

    public static <E extends Identifiable, DTO>
    ResponseEntity<DTO> okOrNotFound(Optional<E> entity, Function<E, DTO> toDto) {
        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DTO dto = entity.map(toDto).get();
        return ResponseEntity.ok(dto);
    }

    public static <DTO> ResponseEntity<DTO> okOrNotFound(boolean found) {
        if (!found) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    public static <E extends Identifiable>
    ResponseEntity created(E entity, HttpServletRequest req) {
        URI accountUri = URI.create(req.getRequestURL().append("/").append(entity.getId()).toString());
        return ResponseEntity.created(accountUri).build();
    }
}
