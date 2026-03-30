package com.techstore.techstore_backend.util;

import com.techstore.techstore_backend.entity.Categoria;
import com.techstore.techstore_backend.entity.Producto;
import com.techstore.techstore_backend.entity.Usuario;
import com.techstore.techstore_backend.entity.enums.Genero;
import com.techstore.techstore_backend.entity.enums.Rol;
import com.techstore.techstore_backend.repository.CategoriaRepository;
import com.techstore.techstore_backend.repository.ProductoRepository;
import com.techstore.techstore_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 1. POBLAR CATEGORÍAS (Si no existen)
        if (categoriaRepository.count() == 0) {
            Categoria teclados = categoriaRepository.save(new Categoria(null, "Teclados"));
            Categoria mouses = categoriaRepository.save(new Categoria(null, "Mouses"));
            Categoria audifonos = categoriaRepository.save(new Categoria(null, "Audífonos"));

            // 2. POBLAR PRODUCTOS: TECLADOS
            productoRepository.save(new Producto(null, "Teclado Mecánico RGB", "Interruptores Blue, layout español", new BigDecimal("150.00"), 20, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859218/1_dc4bdu.jpg", true, teclados));
            productoRepository.save(new Producto(null, "Teclado Inalámbrico Slim", "Ideal para oficina y viajes", new BigDecimal("85.00"), 15, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/99793-99793-2_ovw2zy.jpg", true, teclados));
            productoRepository.save(new Producto(null, "Teclado Gamer Pro TKL", "Sin teclado numérico, switches red", new BigDecimal("210.00"), 12, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/650_1200_tpul61.jpg", true, teclados));
            productoRepository.save(new Producto(null, "Teclado de Membrana Silencioso", "Teclas de perfil bajo, muy cómodo", new BigDecimal("45.00"), 40, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/SUBKBC-0SSK50-Teclado-Business-Slim-Silencioso-con-cable-USB-1_ygvmw5.jpg", true, teclados));
            productoRepository.save(new Producto(null, "Teclado Ergonómico Wave", "Diseño dividido para evitar túnel carpiano", new BigDecimal("130.00"), 8, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859218/Teclado-Logitech-Ergo-Wave-Wireless-Bolt_tluoyw.jpg", true, teclados));

            // 3. POBLAR PRODUCTOS: MOUSES
            productoRepository.save(new Producto(null, "Mouse Gamer 16000 DPI", "Sensor óptico de alta precisión", new BigDecimal("120.00"), 30, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859218/T-Tgm310Black.1_trj9mc.jpg", true, mouses));
            productoRepository.save(new Producto(null, "Mouse Ergonómico Vertical", "Previene fatiga en la muñeca", new BigDecimal("95.00"), 10, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/image-62524546c0e948359611b5ca4c4e1376_jniejo.webp", true, mouses));
            productoRepository.save(new Producto(null, "Mouse Inalámbrico Travel", "Compacto, conexión 2.4GHz y Bluetooth", new BigDecimal("35.00"), 50, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/mouse-185-blue_kswg3w.webp", true, mouses));
            productoRepository.save(new Producto(null, "Mouse Gamer Ultraligero", "Carcasa tipo panal, solo 60 gramos", new BigDecimal("180.00"), 15, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/M75-AIR-WIRELESS-Ultra-Lightweight-Gaming-Mouse-Light-Gray_nw4aof.jpg", true, mouses));
            productoRepository.save(new Producto(null, "Mouse de Oficina Básico", "Conexión USB simple y duradero", new BigDecimal("15.00"), 100, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/51o11IF-yyL._AC_UF894_1000_QL80__tnlle8.jpg", true, mouses));

            // 4. POBLAR PRODUCTOS: AUDÍFONOS
            productoRepository.save(new Producto(null, "Audífonos Noise Cancelling", "Bluetooth 5.2 con cancelación activa", new BigDecimal("450.00"), 12, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859217/6145c1d32e6ac8e63a46c912dc33c5bb_ow8irg.avif", true, audifonos));
            productoRepository.save(new Producto(null, "Headset Gaming 7.1", "Sonido envolvente y micrófono pro", new BigDecimal("280.00"), 25, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859416/71rwPzXKDGL._AC_SL1500__nyhjhk.jpg", true, audifonos));
            productoRepository.save(new Producto(null, "Audífonos In-Ear Deportivos", "Resistentes al sudor, ajuste seguro", new BigDecimal("60.00"), 35, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859417/D_NQ_NP_688064-MLA99522063860_122025-O_czyube.webp", true, audifonos));
            productoRepository.save(new Producto(null, "Audífonos de Estudio Monitoreo", "Sonido plano y fiel para edición", new BigDecimal("320.00"), 10, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859417/81Sh1mHyxvL._AC_UL375_SR375_375__rx4w9y.jpg", true, audifonos));
            productoRepository.save(new Producto(null, "Audífonos Bluetooth Urban", "Estilo moderno y batería de 40 horas", new BigDecimal("140.00"), 20, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774859417/D_NQ_NP_747172-MLA107598678529_022026-O_jg6sxd.webp", true, audifonos));

            System.out.println("Categorías y productos de tecnología insertados.");
        }

        // 5. CREAR USUARIO ADMINISTRADOR (Si no existe)
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNombre("Carlos Sanchez");
            admin.setEmail("admin@techstore.com");
            admin.setContrasenia(passwordEncoder.encode("admin1234"));
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setActivo(true);
            admin.setGenero(Genero.MASCULINO);

            usuarioRepository.save(admin);
            System.out.println("Usuario Administrador creado por defecto.");
        }
    }
}