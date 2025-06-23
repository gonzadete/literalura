
package com.aluracursos.literalura.model;
import com.aluracursos.literalura.DTO.DatosLibro;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne()
    private Autor autor;

    private String idioma;
    private Integer  numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datos, Autor autor) {
        this.titulo = datos.titulo();
        this.autor = autor;
        this.idioma = datos.idioma().get(0);                  //  Elige el primer elemento de un array
        this.numeroDeDescargas = datos.numeroDeDescargas();
    }

// Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
