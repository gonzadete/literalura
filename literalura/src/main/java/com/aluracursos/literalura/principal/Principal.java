package com.aluracursos.literalura.principal;
import com.aluracursos.literalura.DTO.Datos;
import com.aluracursos.literalura.DTO.DatosAutor;
import com.aluracursos.literalura.DTO.DatosLibro;
import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Principal {

    @Autowired
    private final LibroRepository libroRepository;
    @Autowired
    private final AutorRepository autorRepositorio;

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private String json;

    private String menu = """
            \n1 - Buscar libros por titulo 
            2 - Listar libros registrados
            3 - Listar autores registrados
            4 - Listar autores vivos en un determinado año
            5 - Listar libros por idioma
            6 - Top 10 libros mas descargados
            
            0 - Salir
            """;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {
        var opcionElegida = -1;
        while (opcionElegida != 0) {
            json = consumoAPI.obtenerDatos(URL_BASE);
            System.out.println(menu);
            opcionElegida = teclado.nextInt();
            teclado.nextLine();

            switch (opcionElegida) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> findAutoresVivosEn();
                case 5 -> buscarLibrosPorIdioma();
                case 6 -> top10MasDescargados();
                case 0 -> System.out.println("Finalizando");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datosLibro = recibirDatosDelLibro();
        if (datosLibro != null) {
            Libro libro;
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Autor autorExistente = autorRepositorio.findByNombre(datosAutor.nombre());
            if (autorExistente != null) {
                libro = new Libro(datosLibro, autorExistente);
            } else {
                Autor nuevoAutor = new Autor(datosAutor);
                libro = new Libro(datosLibro, nuevoAutor);
                autorRepositorio.save(nuevoAutor);
            }
            try {
                libroRepository.save(libro);
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("El libro ya existe");
            }
        } else {
            System.out.println("El libro no se ha encontrado");
        }
    }

    private DatosLibro recibirDatosDelLibro() {
        System.out.println("Ingrese el titulo  del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(libro -> libro.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            return libroBuscado.get();
        } else {
            return null;
        }
    }

    public void listarLibrosRegistrados() {
        List<Libro> librosRegistrados = libroRepository.findAll();
        if (librosRegistrados.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos");
            return;
        }
        librosRegistrados.forEach(this::imprimirLibro);
    }

    private void imprimirLibro(Libro libro) {
        System.out.println("ID: " + libro.getId());
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Descargas: " + libro.getNumeroDeDescargas());
        System.out.println("Idiomas: " + libro.getIdioma());
        System.out.println("Autor: " + libro.getAutor().getNombre());
        System.out.println("---------------------------");
    }

    public void listarAutoresRegistrados() {
        List<Autor> autoresRegistrados = autorRepositorio.findAll();
        if (autoresRegistrados.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos");
            return;
        }
        autoresRegistrados.forEach(this::imprimirAutor);
    }

    private void imprimirAutor(Autor autor) {
        System.out.println("Autor: " + autor.getNombre());
        System.out.println("ID: " + autor.getId());
        System.out.println("Año nacimiento: " + autor.getFechaNacimiento());
        System.out.println("Año fallecimiento: " + autor.getFechaFallecimiento());

        System.out.println("---------------------------");
    }

    // Buscar autores vivos en un año
    private void findAutoresVivosEn() {
        System.out.println("Ingrese año para buscar autores vivos: ");
        int anio = teclado.nextInt();

        List<Autor> vivos = autorRepositorio.findAutoresVivosEn(anio);
        if (vivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
            return;
        }
        System.out.println("\nAutores vivos en " + anio + ":");
        vivos.forEach(a -> System.out.println("- " + a.getNombre()));
    }

    private void buscarLibrosPorIdioma() {
        String[] lengua = new String[5];
        lengua[0] = "esEspañol  ";
        lengua[1] = "enInglés   ";
        lengua[2] = "ptPortugués";
        lengua[3] = "frFrancés  ";
        lengua[4] = "alAlemán   ";

        System.out.println("\nIngrese el idioma");
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n1 - Español
                    2 - Inglés
                    3 - Portugués
                    4 - Francés
                    5 - Alemán
                    
                    0 - Volver
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            String codLang = lengua[opcion - 1].substring(0, 2);
            String nomLang = lengua[opcion - 1].substring(2, 11);

            List<Libro> librosPorIdioma = libroRepository.findByIdiomaContaining(codLang);
            if (librosPorIdioma.isEmpty()) {
                System.out.println("\nNo se encontraron libros en " + nomLang);
                return;
            }
            System.out.println("\nLibros en " + nomLang);
            librosPorIdioma.forEach(a -> System.out.println("- " + a.getTitulo()));
        }
    }

    private void top10MasDescargados(){
        List<Libro> topDescargas = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();
        topDescargas.forEach(s -> System.out.println("Libro: " + s.getTitulo() + " Descargas: " + s.getNumeroDeDescargas()) );
    }

}
