package com.tienda.apiproductos.controlador;

import com.tienda.apiproductos.modelo.Producto;
import com.tienda.apiproductos.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // CREAR
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto guardado = productoRepository.save(producto);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    // CONSULTAR TODOS
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return new ResponseEntity<>(productoRepository.findAll(), HttpStatus.OK);
    }

    // CONSULTAR UNO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> consultarUno(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto datosNuevos) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNombre(datosNuevos.getNombre());
            producto.setPrecio(datosNuevos.getPrecio());
            producto.setCantidad(datosNuevos.getCantidad());
            Producto actualizado = productoRepository.save(producto);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
