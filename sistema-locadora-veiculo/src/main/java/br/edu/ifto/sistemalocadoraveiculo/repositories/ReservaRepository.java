package br.edu.ifto.sistemalocadoraveiculo.repositories;

import br.edu.ifto.sistemalocadoraveiculo.entidades.Locadora;
import br.edu.ifto.sistemalocadoraveiculo.entidades.Reserva;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource (collectionResourceRel ="reservas", path ="reservas")
public interface ReservaRepository extends PagingAndSortingRepository <Reserva, Long> {

    List<Reserva> findByClienteId(Long id);

    List <Reserva> findByLocadoraRetiradaId(Long id);

    List <Reserva> findByLocadoraDevolucaoId(Long id);

    List <Reserva> findByDataHoraCadastroBetweenAndClienteId(
        @NotNull LocalDateTime dataHoraCadastroInicio,
        @NotNull LocalDateTime dataHoraCadastroFim,
        long id
    );

    List <Reserva> findByLocadoraRetiradaIdAndClienteId(
        long locadoraRetiradaId,
        long id
    );
     @Query
    ("SELECT SUM(r.valorTotal) FROM Reserva r WHERE r.locadoraRetiradaId = :locadoraId AND MONTH(r.dataHoraCadastro) = :mes")
    Double calcularFaturamentoPorLocadoraEMes(@Param("locadoraId") Long locadoraId, @Param("mes") int mes);

    @Query
    ("SELECT COUNT(r) FROM Reserva r WHERE r.locadoraRetiradaId = :locadoraId AND MONTH(r.dataHoraCadastro) = :mes")
    Long calcularTotalReservasPorLocadoraEMes(@Param("locadoraId") Long locadoraId, @Param("mes") int mes);

    @Query
    ("SELECT r.veiculo.categoria, COUNT(r) FROM Reserva r WHERE r.locadoraRetiradaId = :locadoraId AND YEAR(r.dataHoraCadastro) = :ano GROUP BY r.veiculo.categoria")
    List<Object[]> calcularTotalReservasPorLocadoraECategoriaVeiculoEAno(@Param("locadoraId") Long locadoraId, @Param("ano") int ano);

}
