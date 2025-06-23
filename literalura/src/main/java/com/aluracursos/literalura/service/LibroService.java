package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Libro;
import org.springframework.stereotype.Service;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;

@Service
public class LibroService {
    private final LibroRepository libroRepo;
    private final AutorRepository autorRepo;

    public LibroService(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
    }
}
//    public void buscarYGuardarLibro(String titulo) throws Exception {
//        JsonNode libroJson = GutendexClient.buscarLibro(titulo);
//        if (libroJson == null) {
//            System.out.println("No se encontró ningún libro.");
//            return;
//        }
//
//        Long libroId = libroJson.get("id").asLong();
//        if (libroRepo.existsById(libroId)) {
//            System.out.println("El libro ya está almacenado.");
//            return;
//        }
//
//        JsonNode autorJson = libroJson.get("authors").get(0);
//        String nombreAutor = autorJson.get("name").asText();
//
//        Autor autor = autorRepo.findByNombre(nombreAutor).orElseGet(() -> {
//            Autor nuevo = new Autor();
//            nuevo.setNombre(nombreAutor);
//            nuevo.setFechaNacimiento(autorJson.has("birth_year") ? autorJson.get("birth_year").asInt() : null);
//            nuevo.setFechaFallecimiento(autorJson.has("death_year") ? autorJson.get("death_year").asInt() : null);
//            return autorRepo.save(nuevo);
//        });

//        Libro libro = new Libro();
//        libro.setId(libroId);
//        libro.setTitulo(libroJson.get("title").asText());
//        libro.setNumeroDeDescargas(libroJson.get("download_count").asInt());
//
//        List<String> idioma = new ArrayList<>();
//        libroJson.get("languages").forEach(lang -> idioma.add(lang.asText()));
////        libro.setIdioma(idioma);
//
////      libro.setAutorId(autor.getId());
//        libroRepo.save(libro);
//
//        System.out.println("Libro guardado correctamente.");
//    }
//
//    public void listarTodos() {
//        libroRepo.findAll().forEach(this::imprimirLibro);
//    }
//
//    public void buscarPorIdioma(String idioma) {
//        libroRepo.findByIdiomaContaining(idioma).forEach(this::imprimirLibro);
//    }
//
////    public void buscarPorAutor(String autorNombre) {
////        libroRepo.findByAutor_NombreContainingIgnoreCase(autorNombre).forEach(this::imprimirLibro);
////    }
//
//    private void imprimirLibro(Libro libro) {
//        System.out.println("ID: " + libro.getId());
//        System.out.println("Título: " + libro.getTitulo());
//        System.out.println("Descargas: " + libro.getNumeroDeDescargas());
//        System.out.println("Idioma: " + libro.getIdioma());
//        System.out.println("---------------------------");
//    }
//}
