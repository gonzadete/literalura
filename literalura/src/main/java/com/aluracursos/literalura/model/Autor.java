
package com.aluracursos.literalura.model;

import com.aluracursos.literalura.DTO.DatosAutor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();


    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = String.valueOf(datosAutor.nombre());

        try {
            this.fechaNacimiento = datosAutor.fechaNacimiento();
        }
            catch (NumberFormatException e)
            {
            this.fechaNacimiento = 0;
        }


        this.fechaFallecimiento = Integer.valueOf(datosAutor.fechaFallecimiento());
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }
}
