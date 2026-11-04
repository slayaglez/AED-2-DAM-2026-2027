package es.ejemplo.taller.orm.mapper;

import es.ejemplo.taller.modelo.Vehiculo;
import es.ejemplo.taller.orm.entity.VehiculoEntity;

public class VehiculoMapper {

    public static VehiculoEntity toEntity(Vehiculo vehiculo) {
        return new VehiculoEntity(vehiculo.getMatricula()
                , vehiculo.getMarca()
                , vehiculo.getModelo()
                , vehiculo.getAnio()
                , vehiculo.getIdCliente());
    }

    public static Vehiculo toDomain(VehiculoEntity vehiculoEntity) {
        return new Vehiculo(vehiculoEntity.getMatricula()
                , vehiculoEntity.getMarca()
                , vehiculoEntity.getModelo()
                , vehiculoEntity.getAnio()
                , vehiculoEntity.getIdCliente());
    }
}
