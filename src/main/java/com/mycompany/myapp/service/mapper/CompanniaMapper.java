package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CompanniaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compannia} and its DTO {@link CompanniaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanniaMapper extends EntityMapper<CompanniaDTO, Compannia> {



    default Compannia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compannia compannia = new Compannia();
        compannia.setId(id);
        return compannia;
    }
}
