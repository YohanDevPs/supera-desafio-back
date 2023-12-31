package br.com.banco.utils.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UtilModelMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<>();
        for (O o: origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
