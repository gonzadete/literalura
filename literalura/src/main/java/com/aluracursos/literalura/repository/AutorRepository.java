
package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(Integer anio1, Integer anio2);

    @Query("SELECT a FROM Autor a WHERE :anioVivo >= a.fechaNacimiento AND :anioVivo <= a.fechaFallecimiento")
    List<Autor> autorVivoEnAnio(int anioVivo);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND a.fechaFallecimiento >= :anio")
    List<Autor> findAutoresVivosEn(@Param("anio") Integer anio);

}
