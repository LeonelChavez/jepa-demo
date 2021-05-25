package com.teleios;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.teleios.model.Categoria;
import com.teleios.model.Perfil;
import com.teleios.model.Usuario;
import com.teleios.model.Vacante;
import com.teleios.repository.ICategoriaRepository;
import com.teleios.repository.IPerfilesRepository;
import com.teleios.repository.IUsuariosRepository;
import com.teleios.repository.IVacantesRepositiry;

@SpringBootApplication
public class JepaDemoApplication implements CommandLineRunner {
	
	@Autowired
	private ICategoriaRepository iRepositoryCategoria; 

	@Autowired
	private IVacantesRepositiry iRepositoryVacantes; 

	@Autowired
	private IUsuariosRepository iRepositoryUsuarios; 

	@Autowired
	private IPerfilesRepository iRepositoryPerfiles; 

	public static void main(String[] args) {
		SpringApplication.run(JepaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		crearUsuarioConDosPerfiles();
	}
	
	private void crearUsuarioConDosPerfiles() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Leonel Chavez");
		usuario.setEmail("Leonel.Chavez@email.com");
		usuario.setFechaRegistro(new Date());
		usuario.setUsername("lChavez");
		usuario.setPassword("123456");
		usuario.setEstatus(1);
		
		Perfil perfil1 = new Perfil();
		perfil1.setId(2);

		Perfil perfil2 = new Perfil();
		perfil2.setId(3);
		
		usuario.agregar(perfil1);
		usuario.agregar(perfil2);
		
		iRepositoryUsuarios.save(usuario);
		}
	
	private void crearPerfilesAplicacion() {
		iRepositoryPerfiles.saveAll(getPerfilesAplicacion());
	}
	
	private void guardarVacante() {
		Vacante vacante =  new Vacante();
		vacante.setNombre("Profesor de Matemáticas");
		vacante.setDescripcion("Escuela primaria solicita profesor para cursos de Matemáticas");
		vacante.setFecha(new Date());
		vacante.setSalario(8500.00);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setEstatus("escuela.png");
		vacante.setDetalles("<h1>Los requisitos para profesor de Matemáticas</h1>");
		Categoria categoria = new Categoria();
		categoria.setId(14);  // No es necesario dar lo demás atributos de categoría
		vacante.setCategoria(categoria);
		iRepositoryVacantes.save(vacante);
	}
	
	private void buscarVacantes() {
		List<Vacante> lista = iRepositoryVacantes.findAll();
		for (Vacante vacante : lista) {
			System.out.println(vacante.getId() + " " + vacante.getNombre() + " -> " + vacante.getCategoria().getNombre());
		}
	}
	
	private void guardarTodos() {
		List<Categoria> categorias = getListacategorias();
		iRepositoryCategoria.saveAll(categorias);
	}
	
	private List<Categoria> getListacategorias(){
		List<Categoria>  lista = new LinkedList<Categoria>();
		// Categoría 1 
		Categoria categoria1 = new Categoria();
		categoria1.setNombre("Programador de Blockchain");
		categoria1.setDescripcion("Trabajos relacionados con Bitcoin y criptomonedas.");

		// Categoría 2 
		Categoria categoria2 = new Categoria();
		categoria2.setNombre("Soldaor/Pintura");
		categoria2.setDescripcion("Trabajos relacionados con soldadura, pintura y enderezado.");

		//Categoría 3 
		Categoria categoria3 = new Categoria();
		categoria3.setNombre("Ingeniero Industrial");
		categoria3.setDescripcion("Trabajos relacionados con Ingeniería Industrial.");
		
		lista.add(categoria1);
		lista.add(categoria2);
		lista.add(categoria3);
		return lista;
	}
	
	private void existeId() {
		boolean existe = iRepositoryCategoria.existsById(50);
		System.out.println("¿La categoría existe?: " +  existe);
	}
	
	private void buscarTodosOrdenadosJpa() {		
		//List <Categoria> categorias = iRepositoryCategoria.findAll(Sort.by("nombre"));
		List <Categoria> categorias = iRepositoryCategoria.findAll(Sort.by("nombre").descending());
		for (Categoria categoria : categorias) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}

	private void buscarTodosPaginacionOrdenacionJpa() {	
		Page<Categoria> categoriasPage = iRepositoryCategoria.findAll(PageRequest.of(2, 5,Sort.by("nombre").descending()));
		System.out.println("Total de registros: " + categoriasPage.getTotalElements());
		System.out.println("Total de páginas: " + categoriasPage.getTotalPages());
		for (Categoria categoria : categoriasPage.getContent()) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}

	private void buscarTodosPaginacionJpa() {	
		Page<Categoria> categoriasPage = iRepositoryCategoria.findAll(PageRequest.of(0, 5));
		System.out.println("Total de registros: " + categoriasPage.getTotalElements());
		System.out.println("Total de páginas: " + categoriasPage.getTotalPages());
		for (Categoria categoria : categoriasPage.getContent()) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}

	private void buscarTodosJpa() {
		
		List <Categoria> categorias = iRepositoryCategoria.findAll();
		for (Categoria categoria : categorias) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}
	
	private void buscarTodos() {
		Iterable<Categoria> categorias = iRepositoryCategoria.findAll();
		for ( Categoria categoria : categorias) {
			System.out.println( categoria );
		}
	}
	
	private void encontrarPorIds() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias = iRepositoryCategoria.findAllById(ids);
		
		for (Categoria cat : categorias) {
			System.out.println(cat);
		}
	}
	
	private void eliminarTodosJpa(){
		// mas eficiente que eliminarTodos()
		iRepositoryCategoria.deleteAllInBatch();
	}
	private void eliminarTodos() {
		iRepositoryCategoria.deleteAll();
	}
	
	private void buscarPorId() {
		Optional<Categoria> categoria = iRepositoryCategoria.findById(5);
		if ( categoria.isPresent() )
			System.out.println(categoria);
		else
			System.out.println("Categoría no encontrada");
	}
	
	private void conteo() {
		long count = iRepositoryCategoria.count();
		System.out.println("Total de categorías: " + count);
	}
	
	private void eliminar() {
		int idCategoria = 1;
		iRepositoryCategoria.deleteById(idCategoria);
	}
	
	private void modificar() {
		Optional<Categoria> categoria = iRepositoryCategoria.findById(1);
		if ( categoria.isPresent() ) {
			Categoria categoriaTmp =  categoria.get();
			categoriaTmp.setNombre("Ingeniería de software");
			categoriaTmp.setDescripcion("Desarrollo de Sistemas");
			iRepositoryCategoria.save(categoriaTmp);
			System.out.println(categoria);
		}
		else
			System.out.println("Categoría no encontrada");
	}

	private void guardar() {
		Categoria categoria = new Categoria();
		categoria.setNombre("Finanzas");
		categoria.setDescripcion("Trabajos Relacionados con finanzas y contabilidad");
		iRepositoryCategoria.save(categoria);
		System.out.println(categoria);
	}
	
	private List<Perfil> getPerfilesAplicacion(){
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil perfil1 = new Perfil();
		perfil1.setPerfil("Supervisor");

		Perfil perfil2 = new Perfil();
		perfil2.setPerfil("Administrador");

		Perfil perfil3 = new Perfil();
		perfil3.setPerfil("Usuario");
		
		lista.add(perfil1);
		lista.add(perfil2);
		lista.add(perfil3);

		return lista;
	}
}
