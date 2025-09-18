package com.banco.clientepersona;

import com.banco.clientepersona.domain.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    void testClienteCreation() {
        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setNombre("Jose Lema");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Otavalo sn y principal");
        cliente.setTelefono("098254785");
        cliente.setPassword("1234");
        cliente.setEstado(true);

        assertEquals(1L, cliente.getClienteId());
        assertEquals("Jose Lema", cliente.getNombre());
        assertEquals("M", cliente.getGenero());
        assertEquals(30, cliente.getEdad());
        assertEquals("1234567890", cliente.getIdentificacion());
        assertEquals("Otavalo sn y principal", cliente.getDireccion());
        assertEquals("098254785", cliente.getTelefono());
        assertEquals("1234", cliente.getPassword());
        assertTrue(cliente.getEstado());
    }
}
