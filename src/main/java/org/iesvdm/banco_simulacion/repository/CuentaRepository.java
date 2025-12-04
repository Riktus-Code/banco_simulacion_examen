package org.iesvdm.banco_simulacion.repository;

import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CuentaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CuentaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //metodo para mostrar todas las cuentas
    public List<CuentaOrigen> findAll(){
        List<CuentaOrigen> cuentas = jdbcTemplate.query("""
                select * from cuenta_origen;
                """, (rs, rowNum) -> new CuentaOrigen(
                        rs.getLong("id"),
                rs.getString("alias_cuenta"),
                rs.getString("iban")
        )
        );
        return cuentas;
    }

    public List<TransferenciaProgramada> findAllTrans (){
        List<TransferenciaProgramada> transferencias = jdbcTemplate.query("""
                select * from transferencia_programada
                """, (rs, rowNum) -> new TransferenciaProgramada(
                rs.getLong("id"),
                rs.getLong("cuenta_origen_id"),
                rs.getString("nombre_beneficiario"),
                rs.getString("iban_destino"),
                rs.getBigDecimal("importe"),
                rs.getString("concepto"),
                rs.getTimestamp("fecha_programada").toLocalDateTime(),
                rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                rs.getString("estado")
        )
        );
        return transferencias;
    }

    public TransferenciaProgramada findById(Long id){
        return jdbcTemplate.queryForObject("""
                select * from transferencia_programada where id=?;
                """, new Object[] {id},
                (rs, rowNum) ->new TransferenciaProgramada(
                        rs.getLong("id"),
                        rs.getLong("cuenta_origen_id"),
                        rs.getString("nombre_beneficiario"),
                        rs.getString("iban_destino"),
                        rs.getBigDecimal("importe"),
                        rs.getString("concepto"),
                        rs.getTimestamp("fecha_programada").toLocalDateTime(),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getString("estado")
                )
        );
    }

    public CuentaOrigen  findByIdCuenta(Long id){
        return jdbcTemplate.queryForObject("""
                select * from cuenta_origen where id=?;
                """, new Object[] {id},
                (rs, rowNum) ->new CuentaOrigen(
                        rs.getLong("id"),
                        rs.getString("alias_cuenta"),
                        rs.getString("iban")
                )
        );
    }

    public void crearTrans(TransferenciaProgramada transferenciaProgramada) {
        String sql = """
                insert into transferencia_programada (cuenta_origen_id,nombre_beneficiario,iban_destino,
                importe, concepto, fecha_programada, fecha_creacion, estado) values (?,?,?,?,?,?,?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String [] ids = {"id"};

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,ids);
            ps.setLong(1,transferenciaProgramada.getCuentaOrigenId());
            ps.setString(2,transferenciaProgramada.getNombreBeneficiario());
            ps.setString(3,transferenciaProgramada.getIbanDestino());
            ps.setBigDecimal(4,transferenciaProgramada.getImporte());
            ps.setString(5,transferenciaProgramada.getConcepto());
            ps.setTimestamp(6, Timestamp.valueOf(transferenciaProgramada.getFechaProgramada()));
            ps.setTimestamp(7,Timestamp.valueOf(transferenciaProgramada.getFechaCreacion()));
            ps.setString(8,transferenciaProgramada.getEstado());
            return ps;


        }, keyHolder);
        transferenciaProgramada.setId(keyHolder.getKey().longValue());

    }

    public void updateTrans(TransferenciaProgramada transferenciaProgramada){
        String sql = """
                update transferencia_programada set cuenta_origen_id=?, nombre_beneficiario=?, iban_destino=?,
                importe=?, concepto=?, fecha_programada=?, fecha_creacion=?, estado=? where id=?
                """;
        jdbcTemplate.update(sql,
                transferenciaProgramada.getCuentaOrigenId(),
                transferenciaProgramada.getNombreBeneficiario(),
                transferenciaProgramada.getIbanDestino(),
                transferenciaProgramada.getImporte(),
                transferenciaProgramada.getConcepto(),
                transferenciaProgramada.getFechaProgramada(),
                transferenciaProgramada.getFechaCreacion(),
                transferenciaProgramada.getEstado(),
                transferenciaProgramada.getId()

        );
    }

    public void eliminarTrans(Long id){
        int rowUp = jdbcTemplate.update("""
        delete from transferencia_programada where id=? 
            """,id);
    }
}
